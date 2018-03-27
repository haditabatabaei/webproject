
#include "wx/wxprec.h"

#include "wx/frame.h"

#ifndef WX_PRECOMP
    #include "wx/menu.h"
    #include "wx/toolbar.h"
    #include "wx/statusbr.h"
#endif 
#include <gtk/gtk.h>
#include "wx/gtk/private/gtk2-compat.h"




void wxFrame::Init()
{
    m_fsSaveFlag = 0;
}

bool wxFrame::Create( wxWindow *parent,
                      wxWindowID id,
                      const wxString& title,
                      const wxPoint& pos,
                      const wxSize& sizeOrig,
                      long style,
                      const wxString &name )
{
    return wxFrameBase::Create(parent, id, title, pos, sizeOrig, style, name);
}


void wxFrame::DoGetClientSize( int *width, int *height ) const
{
    wxASSERT_MSG( (m_widget != NULL), wxT("invalid frame") );

    wxFrameBase::DoGetClientSize(width, height);

    if (m_useCachedClientSize)
        return;

    if (height)
    {
#if wxUSE_MENUS_NATIVE
                if (m_frameMenuBar && m_frameMenuBar->IsShown())
        {
            int h;
            gtk_widget_get_preferred_height(m_frameMenuBar->m_widget, NULL, &h);
            *height -= h;
        }
#endif 
#if wxUSE_STATUSBAR
                if (m_frameStatusBar && m_frameStatusBar->IsShown())
            *height -= m_frameStatusBar->m_height;
#endif     }

#if wxUSE_TOOLBAR
        if (m_frameToolBar && m_frameToolBar->IsShown())
    {
        if (m_frameToolBar->IsVertical())
        {
            if (width)
            {
                int w;
                gtk_widget_get_preferred_width(m_frameToolBar->m_widget, NULL, &w);
                *width -= w;
            }
        }
        else
        {
            if (height)
            {
                int h;
                gtk_widget_get_preferred_height(m_frameToolBar->m_widget, NULL, &h);
                *height -= h;
            }
        }
    }
#endif 
    if (width != NULL && *width < 0)
        *width = 0;
    if (height != NULL && *height < 0)
        *height = 0;
}

#if wxUSE_MENUS && wxUSE_ACCEL
static void wxAddAccelerators(wxList& accelEntries, wxMenu* menu)
{
    size_t i;
    for (i = 0; i < menu->GetMenuItems().GetCount(); i++)
    {
        wxMenuItem* item = (wxMenuItem*) menu->GetMenuItems().Item(i)->GetData();
        if (item->GetSubMenu())
        {
            wxAddAccelerators(accelEntries, item->GetSubMenu());
        }
        else if (!item->GetItemLabel().IsEmpty())
        {
            wxAcceleratorEntry* entry = wxAcceleratorEntry::Create(item->GetItemLabel());
            if (entry)
            {
                entry->Set(entry->GetFlags(), entry->GetKeyCode(), item->GetId());
                accelEntries.Append((wxObject*) entry);
            }
        }
    }
}

static wxAcceleratorTable wxCreateAcceleratorTableForMenuBar(wxMenuBar* menuBar)
{
    wxList accelEntries;

    size_t i;
    for (i = 0; i < menuBar->GetMenuCount(); i++)
    {
        wxAddAccelerators(accelEntries, menuBar->GetMenu(i));
    }

    size_t n = accelEntries.GetCount();

    if (n == 0)
        return wxAcceleratorTable();

    wxAcceleratorEntry* entries = new wxAcceleratorEntry[n];

    for (i = 0; i < accelEntries.GetCount(); i++)
    {
        wxAcceleratorEntry* entry = (wxAcceleratorEntry*) accelEntries.Item(i)->GetData();
        entries[i] = (*entry);
        delete entry;

    }

    wxAcceleratorTable table(n, entries);
    delete[] entries;

    return table;
}
#endif 
bool wxFrame::ShowFullScreen(bool show, long style)
{
    if (!wxFrameBase::ShowFullScreen(show, style))
        return false;

#if wxUSE_MENUS && wxUSE_ACCEL
    if (show && GetMenuBar())
    {
        wxAcceleratorTable table(wxCreateAcceleratorTableForMenuBar(GetMenuBar()));
        if (table.IsOk())
            SetAcceleratorTable(table);
    }
#endif 
    wxWindow* const bar[] = {
#if wxUSE_MENUS
        m_frameMenuBar,
#else
        NULL,
#endif
#if wxUSE_TOOLBAR
        m_frameToolBar,
#else
        NULL,
#endif
#if wxUSE_STATUSBAR
        m_frameStatusBar,
#else
        NULL,
#endif
    };
    const long fsNoBar[] = {
        wxFULLSCREEN_NOMENUBAR, wxFULLSCREEN_NOTOOLBAR, wxFULLSCREEN_NOSTATUSBAR
    };
    for (int i = 0; i < 3; i++)
    {
        if (show)
        {
            if (bar[i] && (style & fsNoBar[i]))
            {
                if (bar[i]->IsShown())
                    bar[i]->Show(false);
                else
                    style &= ~fsNoBar[i];
            }
        }
        else
        {
            if (bar[i] && (m_fsSaveFlag & fsNoBar[i]))
                bar[i]->Show(true);
        }
    }
    if (show)
        m_fsSaveFlag = style;

    return true;
}

bool wxFrame::SendIdleEvents(wxIdleEvent& event)
{
    bool needMore = wxFrameBase::SendIdleEvents(event);

#if wxUSE_MENUS
    if (m_frameMenuBar && m_frameMenuBar->SendIdleEvents(event))
        needMore = true;
#endif
#if wxUSE_TOOLBAR
    if (m_frameToolBar && m_frameToolBar->SendIdleEvents(event))
        needMore = true;
#endif
#if wxUSE_STATUSBAR
    if (m_frameStatusBar && m_frameStatusBar->SendIdleEvents(event))
        needMore = true;
#endif

    return needMore;
}


#if wxUSE_MENUS_NATIVE

void wxFrame::DetachMenuBar()
{
    wxASSERT_MSG( (m_widget != NULL), wxT("invalid frame") );
    wxASSERT_MSG( (m_wxwindow != NULL), wxT("invalid frame") );

    if ( m_frameMenuBar )
    {
        g_object_ref( m_frameMenuBar->m_widget );

        gtk_container_remove( GTK_CONTAINER(m_mainWidget), m_frameMenuBar->m_widget );
    }

    wxFrameBase::DetachMenuBar();

        m_useCachedClientSize = false;
    m_clientWidth = 0;
}

void wxFrame::AttachMenuBar( wxMenuBar *menuBar )
{
    wxFrameBase::AttachMenuBar(menuBar);

    if (m_frameMenuBar)
    {
                gtk_box_pack_start(
            GTK_BOX(m_mainWidget), menuBar->m_widget, false, false, 0);
        gtk_box_reorder_child(GTK_BOX(m_mainWidget), menuBar->m_widget, 0);

                gtk_widget_set_size_request(menuBar->m_widget, -1, -1);

        gtk_widget_show( m_frameMenuBar->m_widget );
    }
        m_useCachedClientSize = false;
    m_clientWidth = 0;
}
#endif 
#if wxUSE_TOOLBAR

void wxFrame::SetToolBar(wxToolBar *toolbar)
{
    m_frameToolBar = toolbar;
    if (toolbar)
    {
        gtk_container_remove(
            GTK_CONTAINER(gtk_widget_get_parent(toolbar->m_widget)), toolbar->m_widget);
        if (toolbar->IsVertical())
        {
                                    GtkWidget* hbox = gtk_widget_get_parent(m_wxwindow);
            if (hbox == m_mainWidget)
            {
                hbox = gtk_box_new(GTK_ORIENTATION_HORIZONTAL, 0);
                gtk_widget_show(hbox);
                gtk_box_pack_start(GTK_BOX(m_mainWidget), hbox, true, true, 0);
                g_object_ref(m_wxwindow);
                gtk_container_remove(GTK_CONTAINER(m_mainWidget), m_wxwindow);
                gtk_box_pack_start(GTK_BOX(hbox), m_wxwindow, true, true, 0);
                g_object_unref(m_wxwindow);
            }
            gtk_box_pack_start(GTK_BOX(hbox), toolbar->m_widget, false, false, 0);

            int pos = 0;              if (toolbar->HasFlag(wxTB_RIGHT))
                pos = 1;              gtk_box_reorder_child(GTK_BOX(hbox), toolbar->m_widget, pos);
        }
        else
        {
                        gtk_box_pack_start(GTK_BOX(m_mainWidget), toolbar->m_widget, false, false, 0);

            int pos = 0;              if (m_frameMenuBar)
                pos = 1;              if (toolbar->HasFlag(wxTB_BOTTOM))
                pos += 2;              gtk_box_reorder_child(
                GTK_BOX(m_mainWidget), toolbar->m_widget, pos);
        }
                gtk_widget_set_size_request(toolbar->m_widget, -1, -1);
    }
        m_useCachedClientSize = false;
    m_clientWidth = 0;
}

#endif 
#if wxUSE_STATUSBAR

void wxFrame::SetStatusBar(wxStatusBar *statbar)
{
    m_frameStatusBar = statbar;
    if (statbar)
    {
                gtk_container_remove(
            GTK_CONTAINER(gtk_widget_get_parent(statbar->m_widget)), statbar->m_widget);
        gtk_box_pack_end(GTK_BOX(m_mainWidget), statbar->m_widget, false, false, 0);
                statbar->m_useCachedClientSize = false;
        statbar->m_clientWidth = 0;
        int h = -1;
        if (statbar->m_wxwindow)
        {
                        h = statbar->m_height;
        }
        gtk_widget_set_size_request(statbar->m_widget, -1, h);
    }
        m_useCachedClientSize = false;
    m_clientWidth = 0;
}
#endif 