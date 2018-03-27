
#include "wx/wxprec.h"

#ifdef __BORLANDC__
    #pragma hdrstop
#endif

#ifndef WX_PRECOMP
    #include "wx/string.h"
    #include "wx/wxcrtvararg.h"
    #include "wx/log.h"
    #include "wx/intl.h"
#endif

#include "wx/fontutil.h"
#include "wx/fontmap.h"
#include "wx/encinfo.h"
#include "wx/tokenzr.h"


bool wxNativeEncodingInfo::FromString( const wxString& s )
{
    wxStringTokenizer tokenizer(s, wxT(";"));

    facename = tokenizer.GetNextToken();
    if ( !facename )
        return false;

    wxString tmp = tokenizer.GetNextToken();
    if ( !tmp )
    {
                        charset = 0;
    }
    else
    {
        if ( wxSscanf( tmp, wxT("%u"), &charset ) != 1 )
                        return false;
    }

    return true;
}

wxString wxNativeEncodingInfo::ToString() const
{
    wxString s(facename);
    if ( charset != 0 )
        s << wxT(';') << charset;

    return s;
}


bool wxGetNativeFontEncoding( wxFontEncoding encoding, wxNativeEncodingInfo *info )
{
    wxCHECK_MSG( info, false, wxT("bad pointer in wxGetNativeFontEncoding") );

    if ( encoding == wxFONTENCODING_DEFAULT )
        encoding = wxFont::GetDefaultEncoding();

    info->encoding = encoding;

    return true;
}

bool wxTestFontEncoding( const wxNativeEncodingInfo& WXUNUSED(info) )
{
        return true;
}
