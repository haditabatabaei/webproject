


#include "wx/wxprec.h"

#ifdef __BORLANDC__
    #pragma hdrstop
#endif

#if wxUSE_DIRDLG

#if wxUSE_OLE

#include "wx/dirdlg.h"
#include "wx/modalhook.h"

#ifndef WX_PRECOMP
    #include "wx/utils.h"
    #include "wx/dialog.h"
    #include "wx/log.h"
    #include "wx/app.h"     #endif

#include "wx/msw/private.h"
#include "wx/msw/wrapshl.h"
#include "wx/msw/private/comptr.h"
#include "wx/dynlib.h"

#include <initguid.h>

#if wxUSE_DYNLIB_CLASS
    #define wxUSE_IFILEDIALOG 1
#else
    #define wxUSE_IFILEDIALOG 0
#endif

#if wxUSE_IFILEDIALOG

#ifndef __IShellItem_INTERFACE_DEFINED__

#ifndef SIGDN_FILESYSPATH
    #define SIGDN_FILESYSPATH 0x80058000
#endif

struct IShellItem : public IUnknown
{
    virtual HRESULT wxSTDCALL BindToHandler(IBindCtx*, REFGUID, REFIID, void**) = 0;
    virtual HRESULT wxSTDCALL GetParent(IShellItem**) = 0;
    virtual HRESULT wxSTDCALL GetDisplayName(DWORD, LPWSTR*) = 0;
    virtual HRESULT wxSTDCALL GetAttributes(ULONG, ULONG*) = 0;
    virtual HRESULT wxSTDCALL Compare(IShellItem*, DWORD, int*) = 0;
};

#endif 
DEFINE_GUID(IID_IShellItem,
    0x43826D1E, 0xE718, 0x42EE, 0xBC, 0x55, 0xA1, 0xE2, 0x61, 0xC3, 0x7B, 0xFE);

struct IShellItemFilter;
struct IFileDialogEvents;

#ifndef __IModalWindow_INTERFACE_DEFINED__

struct IModalWindow : public IUnknown
{
    virtual HRESULT wxSTDCALL Show(HWND) = 0;
};

#endif 
#ifndef __IFileDialog_INTERFACE_DEFINED__

#ifndef FOS_PICKFOLDERS
    #define FOS_PICKFOLDERS 0x20
#endif

#ifndef FOS_FORCEFILESYSTEM
    #define FOS_FORCEFILESYSTEM 0x40
#endif

struct _COMDLG_FILTERSPEC;

struct IFileDialog : public IModalWindow
{
    virtual HRESULT wxSTDCALL SetFileTypes(UINT, const _COMDLG_FILTERSPEC*) = 0;
    virtual HRESULT wxSTDCALL SetFileTypeIndex(UINT) = 0;
    virtual HRESULT wxSTDCALL GetFileTypeIndex(UINT*) = 0;
    virtual HRESULT wxSTDCALL Advise(IFileDialogEvents*, DWORD*) = 0;
    virtual HRESULT wxSTDCALL Unadvise(DWORD) = 0;
    virtual HRESULT wxSTDCALL SetOptions(DWORD) = 0;
    virtual HRESULT wxSTDCALL GetOptions(DWORD*) = 0;
    virtual HRESULT wxSTDCALL SetDefaultFolder(IShellItem*) = 0;
    virtual HRESULT wxSTDCALL SetFolder(IShellItem*) = 0;
    virtual HRESULT wxSTDCALL GetFolder(IShellItem**) = 0;
    virtual HRESULT wxSTDCALL GetCurrentSelection(IShellItem**) = 0;
    virtual HRESULT wxSTDCALL SetFileName(LPCWSTR) = 0;
    virtual HRESULT wxSTDCALL GetFileName(LPWSTR*) = 0;
    virtual HRESULT wxSTDCALL SetTitle(LPCWSTR) = 0;
    virtual HRESULT wxSTDCALL SetOkButtonLabel(LPCWSTR) = 0;
    virtual HRESULT wxSTDCALL SetFileNameLabel(LPCWSTR) = 0;
    virtual HRESULT wxSTDCALL GetResult(IShellItem**) = 0;
    virtual HRESULT wxSTDCALL AddPlace(IShellItem*, DWORD) = 0;
    virtual HRESULT wxSTDCALL SetDefaultExtension(LPCWSTR) = 0;
    virtual HRESULT wxSTDCALL Close(HRESULT) = 0;
    virtual HRESULT wxSTDCALL SetClientGuid(REFGUID) = 0;
    virtual HRESULT wxSTDCALL ClearClientData() = 0;
    virtual HRESULT wxSTDCALL SetFilter(IShellItemFilter*) = 0;
};

DEFINE_GUID(CLSID_FileOpenDialog,
    0xDC1C5A9C, 0xE88A, 0x4dde, 0xA5, 0xA1, 0x60, 0xF8, 0x2A, 0x20, 0xAE, 0xF7);

DEFINE_GUID(IID_IFileDialog,
    0x42F85136, 0xDB7E, 0x439C, 0x85, 0xF1, 0xE4, 0x07, 0x5D, 0x13, 0x5F, 0xC8);

#endif 
#endif 

#ifndef BIF_NEWDIALOGSTYLE
    #define BIF_NEWDIALOGSTYLE 0x0040
#endif

#ifndef BIF_NONEWFOLDERBUTTON
    #define BIF_NONEWFOLDERBUTTON  0x0200
#endif

#ifndef BIF_EDITBOX
    #define BIF_EDITBOX 16
#endif


wxIMPLEMENT_CLASS(wxDirDialog, wxDialog);


static int CALLBACK BrowseCallbackProc(HWND hwnd, UINT uMsg, LPARAM lp,
                                       LPARAM pData);




wxDirDialog::wxDirDialog(wxWindow *parent,
                         const wxString& message,
                         const wxString& defaultPath,
                         long style,
                         const wxPoint& WXUNUSED(pos),
                         const wxSize& WXUNUSED(size),
                         const wxString& WXUNUSED(name))
{
    m_message = message;
    m_parent = parent;

    SetWindowStyle(style);
    SetPath(defaultPath);
}

void wxDirDialog::SetPath(const wxString& path)
{
    m_path = path;

        m_path.Replace(wxT("/"), wxT("\\"));

    while ( !m_path.empty() && (*(m_path.end() - 1) == wxT('\\')) )
    {
        m_path.erase(m_path.length() - 1);
    }

            if ( !m_path.empty() && (*(m_path.end() - 1) == wxT(':')) )
    {
        m_path += wxT('\\');
    }
}

int wxDirDialog::ShowModal()
{
    WX_HOOK_MODAL_DIALOG();

    wxWindow* const parent = GetParentForModalDialog();
    WXHWND hWndParent = parent ? GetHwndOf(parent) : NULL;

        int rc;
#if wxUSE_IFILEDIALOG
                        if ( wxGetWinVersion() > wxWinVersion_Vista )
    {
        rc = ShowIFileDialog(hWndParent);
    }
    else
    {
        rc = wxID_NONE;
    }

    if ( rc == wxID_NONE )
#endif     {
        rc = ShowSHBrowseForFolder(hWndParent);
    }

        if ( rc == wxID_OK && HasFlag(wxDD_CHANGE_DIR) )
        wxSetWorkingDirectory(m_path);

    return rc;
}

int wxDirDialog::ShowSHBrowseForFolder(WXHWND owner)
{
    BROWSEINFO bi;
    bi.hwndOwner      = owner;
    bi.pidlRoot       = NULL;
    bi.pszDisplayName = NULL;
    bi.lpszTitle      = m_message.c_str();
    bi.ulFlags        = BIF_RETURNONLYFSDIRS | BIF_STATUSTEXT;
    bi.lpfn           = BrowseCallbackProc;
    bi.lParam         = wxMSW_CONV_LPARAM(m_path); 
    static const int verComCtl32 = wxApp::GetComCtl32Version();

        bi.ulFlags |= BIF_EDITBOX;

                const bool needNewDir = !HasFlag(wxDD_DIR_MUST_EXIST);
    if ( needNewDir || HasFlag(wxRESIZE_BORDER) )
    {
        if (needNewDir)
        {
            bi.ulFlags |= BIF_NEWDIALOGSTYLE;
        }
        else
        {
                                                            if (verComCtl32 >= 600)
            {
                bi.ulFlags |= BIF_NEWDIALOGSTYLE;
                bi.ulFlags |= BIF_NONEWFOLDERBUTTON;
            }
        }
    }

        wxItemIdList pidl(SHBrowseForFolder(&bi));

    wxItemIdList::Free((LPITEMIDLIST)bi.pidlRoot);

    if ( !pidl )
    {
                return wxID_CANCEL;
    }

    m_path = pidl.GetPath();

    return m_path.empty() ? wxID_CANCEL : wxID_OK;
}

#if wxUSE_IFILEDIALOG

int wxDirDialog::ShowIFileDialog(WXHWND owner)
{
    HRESULT hr;
    wxCOMPtr<IFileDialog> fileDialog;

    hr = ::CoCreateInstance(CLSID_FileOpenDialog, NULL, CLSCTX_INPROC_SERVER,
                            wxIID_PPV_ARGS(IFileDialog, &fileDialog));
    if ( FAILED(hr) )
    {
        wxLogApiError(wxS("CoCreateInstance(CLSID_FileOpenDialog)"), hr);
        return wxID_NONE;
    }

        hr = fileDialog->SetOptions(FOS_PICKFOLDERS | FOS_FORCEFILESYSTEM);
    if ( FAILED(hr) )
    {
        wxLogApiError(wxS("IFileDialog::SetOptions"), hr);
        return wxID_NONE;
    }

    hr = fileDialog->SetTitle(m_message.wc_str());
    if ( FAILED(hr) )
    {
                        wxLogApiError(wxS("IFileDialog::SetTitle"), hr);
    }

        if ( !m_path.empty() )
    {
                        typedef HRESULT
        (WINAPI *SHCreateItemFromParsingName_t)(PCWSTR,
                                                IBindCtx*,
                                                REFIID,
                                                void**);

        SHCreateItemFromParsingName_t pfnSHCreateItemFromParsingName = NULL;
        wxDynamicLibrary dllShell32;
        if ( dllShell32.Load(wxS("shell32.dll"), wxDL_VERBATIM | wxDL_QUIET) )
        {
            wxDL_INIT_FUNC(pfn, SHCreateItemFromParsingName, dllShell32);
        }

        if ( !pfnSHCreateItemFromParsingName )
        {
            wxLogLastError(wxS("SHCreateItemFromParsingName() not found"));
            return wxID_NONE;
        }

        wxCOMPtr<IShellItem> folder;
        hr = pfnSHCreateItemFromParsingName(m_path.wc_str(),
                                              NULL,
                                              wxIID_PPV_ARGS(IShellItem,
                                                             &folder));

                                if ( FAILED(hr) )
        {
            if ( hr != HRESULT_FROM_WIN32(ERROR_FILE_NOT_FOUND) )
            {
                wxLogApiError(wxS("SHCreateItemFromParsingName"), hr);
                return wxID_NONE;
            }
        }
        else         {
            hr = fileDialog->SetFolder(folder);
            if ( FAILED(hr) )
            {
                wxLogApiError(wxS("IFileDialog::SetFolder"), hr);
                return wxID_NONE;
            }
        }
    }


    wxString path;

    hr = fileDialog->Show(owner);
    if ( SUCCEEDED(hr) )
    {
        wxCOMPtr<IShellItem> folder;

        hr = fileDialog->GetResult(&folder);
        if ( SUCCEEDED(hr) )
        {
            LPOLESTR pathOLE = NULL;

            hr = folder->GetDisplayName(SIGDN_FILESYSPATH, &pathOLE);
            if ( SUCCEEDED(hr) )
            {
                path = pathOLE;
                CoTaskMemFree(pathOLE);
            }
            else
            {
                wxLogApiError(wxS("IShellItem::GetDisplayName"), hr);
            }
        }
        else
        {
            wxLogApiError(wxS("IFileDialog::GetResult"), hr);
        }
    }
    else if ( hr == HRESULT_FROM_WIN32(ERROR_CANCELLED) )
    {
        return wxID_CANCEL;     }
    else
    {
        wxLogApiError(wxS("IFileDialog::Show"), hr);
    }

    if ( path.empty() )
    {
                                wxLogSysError(_("Couldn't obtain folder name"), hr);
        return wxID_CANCEL;
    }

    m_path = path;
    return wxID_OK;
}

#endif 

static int CALLBACK
BrowseCallbackProc(HWND hwnd, UINT uMsg, LPARAM lp, LPARAM pData)
{
    switch(uMsg)
    {
#ifdef BFFM_SETSELECTION
        case BFFM_INITIALIZED:
                                                            ::SendMessage(hwnd, BFFM_SETSELECTION, TRUE, pData);
            break;
#endif 

        case BFFM_SELCHANGED:
                                                            {
                                wxString strDir;
                if ( SHGetPathFromIDList((LPITEMIDLIST)lp,
                                         wxStringBuffer(strDir, MAX_PATH)) )
                {
                                                            
                                        static const size_t maxChars = 37;
                    if ( strDir.length() > maxChars )
                    {
                        strDir = strDir.Right(maxChars);
                        strDir = wxString(wxT("...")) + strDir;
                    }

                    SendMessage(hwnd, BFFM_SETSTATUSTEXT,
                                0, wxMSW_CONV_LPARAM(strDir));
                }
            }
            break;

                    }

    return 0;
}

#endif 
#endif 