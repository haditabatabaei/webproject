
#ifndef __NUMDLGH_G__
#define __NUMDLGH_G__

#include "wx/defs.h"

#if wxUSE_NUMBERDLG

#include "wx/dialog.h"

#if wxUSE_SPINCTRL
    class WXDLLIMPEXP_FWD_CORE wxSpinCtrl;
#else
    class WXDLLIMPEXP_FWD_CORE wxTextCtrl;
#endif 

class WXDLLIMPEXP_CORE wxNumberEntryDialog : public wxDialog
{
public:
    wxNumberEntryDialog()
    {
        m_value = m_min = m_max = 0;
    }

    wxNumberEntryDialog(wxWindow *parent,
                        const wxString& message,
                        const wxString& prompt,
                        const wxString& caption,
                        long value, long min, long max,
                        const wxPoint& pos = wxDefaultPosition)
    {
        Create(parent, message, prompt, caption, value, min, max, pos);
    }

    bool Create(wxWindow *parent,
                const wxString& message,
                const wxString& prompt,
                const wxString& caption,
                long value, long min, long max,
                const wxPoint& pos = wxDefaultPosition);

    long GetValue() const { return m_value; }

        void OnOK(wxCommandEvent& event);
    void OnCancel(wxCommandEvent& event);

protected:

#if wxUSE_SPINCTRL
    wxSpinCtrl *m_spinctrl;
#else
    wxTextCtrl *m_spinctrl;
#endif 
    long m_value, m_min, m_max;

private:
    wxDECLARE_EVENT_TABLE();
    wxDECLARE_DYNAMIC_CLASS(wxNumberEntryDialog);
    wxDECLARE_NO_COPY_CLASS(wxNumberEntryDialog);
};


WXDLLIMPEXP_CORE long
    wxGetNumberFromUser(const wxString& message,
                        const wxString& prompt,
                        const wxString& caption,
                        long value = 0,
                        long min = 0,
                        long max = 100,
                        wxWindow *parent = NULL,
                        const wxPoint& pos = wxDefaultPosition);

#endif 
#endif 