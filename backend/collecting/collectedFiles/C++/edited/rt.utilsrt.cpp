


#include "wx/wxprec.h"

#if defined(__BORLANDC__)
#pragma hdrstop
#endif

#include "wx/msw/rt/utils.h"

#if wxUSE_WINRT

#include <roapi.h>

#include "wx/dynlib.h"
#include "wx/utils.h"
#include "wx/module.h"

typedef HRESULT (__stdcall *PFNWXROINITIALIZE)(RO_INIT_TYPE initType);
typedef void (__stdcall *PFNWXROUNINITIALIZE)();
typedef HRESULT (__stdcall *PFNWXROGETACTIVATIONFACTORY)(
    HSTRING activatableClassId, REFIID iid, void ** factory);

typedef HRESULT (__stdcall *PFNWXWINDOWSCREATESTRINGREFERENCE)(
    PCWSTR sourceString,
    UINT32 length,
    HSTRING_HEADER * hstringHeader,
    HSTRING * string
);
typedef HRESULT (__stdcall *PFNWXWINDOWSDELETESTRING)(HSTRING string);

namespace wxWinRT
{


class RTCore
{
public:
    static RTCore& Get()
    {
        if ( ms_isAvailable == -1 )
        {
            if ( !ms_rtcore.Initialize() )
            {
                ms_isAvailable = 0;
            }
            else
                ms_isAvailable = 1;
        }

        return ms_rtcore;
    }

    static bool IsAvailable()
    {
        if (ms_isAvailable == -1)
            Get();

        return (ms_isAvailable == 1);
    }

    PFNWXROINITIALIZE RoInitialize;
    PFNWXROUNINITIALIZE RoUninitialize;
    PFNWXROGETACTIVATIONFACTORY RoGetActivationFactory;
    PFNWXWINDOWSCREATESTRINGREFERENCE WindowsCreateStringReference;
    PFNWXWINDOWSDELETESTRING WindowsDeleteString;

    bool Initialize()
    {
#define RESOLVE_RT_FUNCTION(dll, type, funcname)                        \
    funcname = (type)dll.GetSymbol(wxT(#funcname));                     \
    if ( !funcname )                                                    \
        return false

#define RESOLVE_RTCORE_FUNCTION(type, funcname) \
    RESOLVE_RT_FUNCTION(m_dllCore, type, funcname)

#define RESOLVE_RTSTRING_FUNCTION(type, funcname) \
    RESOLVE_RT_FUNCTION(m_dllString, type, funcname)

                if (!wxCheckOsVersion(6, 2))
            return false;

                if (!m_dllCore.Load("api-ms-win-core-winrt-l1-1-0.dll"))
            return false;

        RESOLVE_RTCORE_FUNCTION(PFNWXROINITIALIZE, RoInitialize);
        RESOLVE_RTCORE_FUNCTION(PFNWXROUNINITIALIZE, RoUninitialize);
        RESOLVE_RTCORE_FUNCTION(PFNWXROGETACTIVATIONFACTORY, RoGetActivationFactory);

                if (!m_dllString.Load("api-ms-win-core-winrt-string-l1-1-0.dll"))
            return false;

        RESOLVE_RTSTRING_FUNCTION(PFNWXWINDOWSCREATESTRINGREFERENCE, WindowsCreateStringReference);
        RESOLVE_RTSTRING_FUNCTION(PFNWXWINDOWSDELETESTRING, WindowsDeleteString);

        return true;

#undef RESOLVE_RT_FUNCTION
#undef RESOLVE_RTCORE_FUNCTION
#undef RESOLVE_RTSTRING_FUNCTION
    }

    void UnloadModules()
    {
        m_dllCore.Unload();
        m_dllString.Unload();
    }

    static void Uninitalize()
    {
        if (ms_isAvailable == 1)
        {
            Get().UnloadModules();
            ms_isAvailable = -1;
        }
    }

private:
    RTCore()
    {

    }

    wxDynamicLibrary m_dllCore;
    wxDynamicLibrary m_dllString;

    static RTCore ms_rtcore;
    static int ms_isAvailable;

    wxDECLARE_NO_COPY_CLASS(RTCore);
};

RTCore RTCore::ms_rtcore;
int RTCore::ms_isAvailable = -1;


const TempStringRef TempStringRef::Make(const wxString &str)
{
    return TempStringRef(str);
}

TempStringRef::TempStringRef(const wxString &str)
{
    if ( !RTCore::IsAvailable() )
        wxLogDebug("Can not create string reference without WinRT");

        HRESULT hr = RTCore::Get().WindowsCreateStringReference(
        str.wc_str(), str.length(),
        &m_header, &m_hstring);
    if ( FAILED(hr) )
        wxLogDebug("Could not create string reference %.8x", hr);
}


bool IsAvailable()
{
    return RTCore::IsAvailable();
}

bool Initialize()
{
    if ( !RTCore::IsAvailable() )
        return false;

    HRESULT hr = RTCore::Get().RoInitialize(RO_INIT_SINGLETHREADED);
    if ( FAILED(hr) )
    {
        wxLogDebug("RoInitialize failed %.8x", hr);
        return false;
    }
    else
        return true;
}

void Uninitialize()
{
    if ( !RTCore::IsAvailable() )
        return;

    RTCore::Get().RoUninitialize();
}

bool GetActivationFactory(const wxString& activatableClassId, REFIID iid, void ** factory)
{
    if ( !RTCore::IsAvailable() )
        return false;

    HRESULT hr = RTCore::Get().RoGetActivationFactory(TempStringRef::Make(activatableClassId), iid, factory);
    if ( FAILED(hr) )
    {
        wxLogDebug("RoGetActivationFactory failed %.8x", hr);
        return false;
    }
    else
        return true;
}


class RTModule : public wxModule
{
public:
    RTModule()
    {
    }

    virtual bool OnInit() wxOVERRIDE
    {
        return true;
    }

    virtual void OnExit() wxOVERRIDE
    {
        RTCore::Uninitalize();
    }

private:
    wxDECLARE_DYNAMIC_CLASS(RTModule);
};

wxIMPLEMENT_DYNAMIC_CLASS(RTModule, wxModule);

} 
#endif 