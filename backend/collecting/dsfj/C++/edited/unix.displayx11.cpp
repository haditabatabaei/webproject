


#include "wx/wxprec.h"

#ifdef __BORLANDC__
    #pragma hdrstop
#endif

#ifndef WX_PRECOMP
    #include "wx/utils.h"
    #include "wx/intl.h"
    #include "wx/log.h"
#endif 

#ifdef __WXGTK20__
    #include <gdk/gdk.h> #endif
#if !defined(__WXGTK20__) || defined(GDK_WINDOWING_X11)
    #include <X11/Xlib.h>
    #include <X11/Xatom.h>
#endif

#if wxUSE_DISPLAY

#include "wx/display.h"
#include "wx/display_impl.h"

#ifndef __WXGTK20__

        #include <X11/extensions/Xinerama.h>

    typedef XineramaScreenInfo ScreenInfo;


class ScreensInfoBase
{
public:
    operator const ScreenInfo *() const { return m_screens; }

    unsigned GetCount() const { return static_cast<unsigned>(m_num); }

protected:
    ScreenInfo *m_screens;
    int m_num;
};

class ScreensInfo : public ScreensInfoBase
{
public:
    ScreensInfo()
    {
        m_screens = XineramaQueryScreens((Display *)wxGetDisplay(), &m_num);
    }

    ~ScreensInfo()
    {
        XFree(m_screens);
    }
};


class wxDisplayImplX11 : public wxDisplayImpl
{
public:
    wxDisplayImplX11(unsigned n, const ScreenInfo& info)
        : wxDisplayImpl(n),
          m_rect(info.x_org, info.y_org, info.width, info.height)
    {
    }

    virtual wxRect GetGeometry() const wxOVERRIDE { return m_rect; }
    virtual wxRect GetClientArea() const wxOVERRIDE
    {
                                return IsPrimary() ? wxGetClientDisplayRect() : m_rect;
    }

    virtual wxString GetName() const wxOVERRIDE { return wxString(); }

    virtual wxArrayVideoModes GetModes(const wxVideoMode& mode) const wxOVERRIDE;
    virtual wxVideoMode GetCurrentMode() const wxOVERRIDE;
    virtual bool ChangeMode(const wxVideoMode& mode) wxOVERRIDE;

private:
    wxRect m_rect;
    int m_depth;

    wxDECLARE_NO_COPY_CLASS(wxDisplayImplX11);
};

class wxDisplayFactoryX11 : public wxDisplayFactory
{
public:
    wxDisplayFactoryX11() { }

    virtual wxDisplayImpl *CreateDisplay(unsigned n) wxOVERRIDE;
    virtual unsigned GetCount() wxOVERRIDE;
    virtual int GetFromPoint(const wxPoint& pt) wxOVERRIDE;

protected:
    wxDECLARE_NO_COPY_CLASS(wxDisplayFactoryX11);
};


unsigned wxDisplayFactoryX11::GetCount()
{
    return ScreensInfo().GetCount();
}

int wxDisplayFactoryX11::GetFromPoint(const wxPoint& p)
{
    ScreensInfo screens;

    const unsigned numscreens(screens.GetCount());
    for ( unsigned i = 0; i < numscreens; ++i )
    {
        const ScreenInfo& s = screens[i];
        if ( p.x >= s.x_org && p.x < s.x_org + s.width &&
                p.y >= s.y_org && p.y < s.y_org + s.height )
        {
            return i;
        }
    }

    return wxNOT_FOUND;
}

wxDisplayImpl *wxDisplayFactoryX11::CreateDisplay(unsigned n)
{
    ScreensInfo screens;

    return n < screens.GetCount() ? new wxDisplayImplX11(n, screens[n]) : NULL;
}
#endif 

#if !defined(__WXGTK20__) || defined(GDK_WINDOWING_X11)

#ifdef HAVE_X11_EXTENSIONS_XF86VMODE_H

#include <X11/extensions/xf86vmode.h>


#define wxClearXVM(vm)  if(vm.privsize) XFree(vm.c_private)

#define wxCRR2(v,dc) (int) (((1000.0f * (float) dc) ) / ((float) v.htotal * v.vtotal ) + 0.5f)
#define wxCRR(v) wxCRR2(v,v.dotclock)
#define wxCVM2(v, dc, display, nScreen) wxVideoMode(v.hdisplay, v.vdisplay, DefaultDepth(display, nScreen), wxCRR2(v,dc))
#define wxCVM(v, display, nScreen) wxCVM2(v, v.dotclock, display, nScreen)

wxArrayVideoModes wxXF86VidMode_GetModes(const wxVideoMode& mode, Display* display, int nScreen)
{
    XF86VidModeModeInfo** ppXModes;     int nNumModes; 
    wxArrayVideoModes Modes; 
    if (XF86VidModeGetAllModeLines(display, nScreen, &nNumModes, &ppXModes))
    {
        for (int i = 0; i < nNumModes; ++i)
        {
            XF86VidModeModeInfo& info = *ppXModes[i];
            const wxVideoMode vm = wxCVM(info, display, nScreen);
            if (vm.Matches(mode))
            {
                Modes.Add(vm);
            }
            wxClearXVM(info);
                }
        XFree(ppXModes);
    }
    else     {
        wxLogSysError(_("Failed to enumerate video modes"));
    }

    return Modes;
}

wxVideoMode wxXF86VidMode_GetCurrentMode(Display* display, int nScreen)
{
  XF86VidModeModeLine VM;
  int nDotClock;
  XF86VidModeGetModeLine(display, nScreen, &nDotClock, &VM);
  wxClearXVM(VM);
  return wxCVM2(VM, nDotClock, display, nScreen);
}

bool wxXF86VidMode_ChangeMode(const wxVideoMode& mode, Display* display, int nScreen)
{
    XF86VidModeModeInfo** ppXModes;     int nNumModes; 
    if(!XF86VidModeGetAllModeLines(display, nScreen, &nNumModes, &ppXModes))
    {
        wxLogSysError(_("Failed to change video mode"));
        return false;
    }

    bool bRet = false;
    if (mode == wxDefaultVideoMode)
    {
        bRet = XF86VidModeSwitchToMode(display, nScreen, ppXModes[0]) != 0;

        for (int i = 0; i < nNumModes; ++i)
        {
            wxClearXVM((*ppXModes[i]));
                }
    }
    else
    {
        for (int i = 0; i < nNumModes; ++i)
        {
            if (!bRet &&
                ppXModes[i]->hdisplay == mode.GetWidth() &&
                ppXModes[i]->vdisplay == mode.GetHeight() &&
                wxCRR((*ppXModes[i])) == mode.GetRefresh())
            {
                                bRet = XF86VidModeSwitchToMode(display, nScreen, ppXModes[i]) != 0;
            }
            wxClearXVM((*ppXModes[i]));
                }
    }

    XFree(ppXModes);

    return bRet;
}

#ifndef __WXGTK20__
wxArrayVideoModes wxDisplayImplX11::GetModes(const wxVideoMode& modeMatch) const
{
    Display* display = static_cast<Display*>(wxGetDisplay());
    int nScreen = DefaultScreen(display);
    return wxXF86VidMode_GetModes(modeMatch, display, nScreen);
}

wxVideoMode wxDisplayImplX11::GetCurrentMode() const
{
    Display* display = static_cast<Display*>(wxGetDisplay());
    int nScreen = DefaultScreen(display);
    return wxXF86VidMode_GetCurrentMode(display, nScreen);
}

bool wxDisplayImplX11::ChangeMode(const wxVideoMode& mode)
{
    Display* display = static_cast<Display*>(wxGetDisplay());
    int nScreen = DefaultScreen(display);
    return wxXF86VidMode_ChangeMode(mode, display, nScreen);
}
#endif 
#else 
wxArrayVideoModes wxX11_GetModes(const wxDisplayImpl* impl, const wxVideoMode& modeMatch, Display* display)
{
    int count_return;
    int* depths = XListDepths(display, 0, &count_return);
    wxArrayVideoModes modes;
    if ( depths )
    {
        const wxRect rect = impl->GetGeometry();
        for ( int x = 0; x < count_return; ++x )
        {
            wxVideoMode mode(rect.width, rect.height, depths[x]);
            if ( mode.Matches(modeMatch) )
            {
                modes.Add(mode);
            }
        }

        XFree(depths);
    }
    return modes;
}

#ifndef __WXGTK20__
wxArrayVideoModes wxDisplayImplX11::GetModes(const wxVideoMode& modeMatch) const
{
    Display* display = static_cast<Display*>(wxGetDisplay());
    return wxX11_GetModes(this, modeMatch, display);
}

wxVideoMode wxDisplayImplX11::GetCurrentMode() const
{
        return wxVideoMode();
}

bool wxDisplayImplX11::ChangeMode(const wxVideoMode& WXUNUSED(mode))
{
        return false;
}
#endif #endif #endif 

#ifndef __WXGTK20__
 wxDisplayFactory *wxDisplay::CreateFactory()
{
    if ( !XineramaIsActive((Display*)wxGetDisplay()) )
        return new wxDisplayFactorySingle;

    return new wxDisplayFactoryX11;
}
#endif

#endif 

#if !defined(__WXGTK20__) || defined(GDK_WINDOWING_X11)
void wxGetWorkAreaX11(Screen* screen, int& x, int& y, int& width, int& height)
{
    Display* display = DisplayOfScreen(screen);
    Atom property = XInternAtom(display, "_NET_WORKAREA", true);
    if (property)
    {
        Atom actual_type;
        int actual_format;
        unsigned long nitems;
        unsigned long bytes_after;
        unsigned char* data = NULL;
        Status status = XGetWindowProperty(
            display, RootWindowOfScreen(screen), property,
            0, 4, false, XA_CARDINAL,
            &actual_type, &actual_format, &nitems, &bytes_after, &data);
        if (status == Success && actual_type == XA_CARDINAL &&
            actual_format == 32 && nitems == 4)
        {
            const long* p = (long*)data;
            x = p[0];
            y = p[1];
            width = p[2];
            height = p[3];
        }
        if (data)
            XFree(data);
    }
}
#endif 
#ifndef __WXGTK20__

void wxClientDisplayRect(int *x, int *y, int *width, int *height)
{
    Display * const dpy = wxGetX11Display();
    wxCHECK_RET( dpy, wxT("can't be called before initializing the GUI") );

    wxRect rectClient;
    wxGetWorkAreaX11(DefaultScreenOfDisplay(dpy),
        rectClient.x, rectClient.y, rectClient.width, rectClient.height);

                wxRect rectFull;
#if wxUSE_DISPLAY
    ScreensInfo screens;
    const ScreenInfo& info = screens[0];
    rectFull = wxRect(info.x_org, info.y_org, info.width, info.height);
#else
    wxDisplaySize(&rectFull.width, &rectFull.height);
#endif

    if ( !rectClient.width || !rectClient.height )
    {
                        rectClient = rectFull;
    }
    else
    {
        rectClient = rectClient.Intersect(rectFull);
    }

    if ( x )
        *x = rectClient.x;
    if ( y )
        *y = rectClient.y;
    if ( width )
        *width = rectClient.width;
    if ( height )
        *height = rectClient.height;
}
#endif 