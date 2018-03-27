


#include "wx/wxprec.h"

#ifdef __BORLANDC__
    #pragma hdrstop
#endif

#include "wx/intl.h"

#if wxUSE_INTL

#ifdef __WIN32__
    #include "wx/msw/private.h"
#endif





#if !defined(__WIN32__)

#define SETWINLANG(info,lang,sublang)

#else

#define SETWINLANG(info,lang,sublang) \
    info.WinLang = lang, info.WinSublang = sublang;

#ifndef LANG_AFRIKAANS
#define LANG_AFRIKAANS (0)
#endif
#ifndef LANG_ALBANIAN
#define LANG_ALBANIAN (0)
#endif
#ifndef LANG_ARABIC
#define LANG_ARABIC (0)
#endif
#ifndef LANG_ARMENIAN
#define LANG_ARMENIAN (0)
#endif
#ifndef LANG_ASSAMESE
#define LANG_ASSAMESE (0)
#endif
#ifndef LANG_AZERI
#define LANG_AZERI (0)
#endif
#ifndef LANG_BASQUE
#define LANG_BASQUE (0)
#endif
#ifndef LANG_BELARUSIAN
#define LANG_BELARUSIAN (0)
#endif
#ifndef LANG_BENGALI
#define LANG_BENGALI (0)
#endif
#ifndef LANG_BOSNIAN
#define LANG_BOSNIAN (0)
#endif
#ifndef LANG_BULGARIAN
#define LANG_BULGARIAN (0)
#endif
#ifndef LANG_CATALAN
#define LANG_CATALAN (0)
#endif
#ifndef LANG_CHINESE
#define LANG_CHINESE (0)
#endif
#ifndef LANG_CROATIAN
#define LANG_CROATIAN (0)
#endif
#ifndef LANG_CZECH
#define LANG_CZECH (0)
#endif
#ifndef LANG_DANISH
#define LANG_DANISH (0)
#endif
#ifndef LANG_DUTCH
#define LANG_DUTCH (0)
#endif
#ifndef LANG_ENGLISH
#define LANG_ENGLISH (0)
#endif
#ifndef LANG_ESTONIAN
#define LANG_ESTONIAN (0)
#endif
#ifndef LANG_FAEROESE
#define LANG_FAEROESE (0)
#endif
#ifndef LANG_FARSI
#define LANG_FARSI (0)
#endif
#ifndef LANG_FINNISH
#define LANG_FINNISH (0)
#endif
#ifndef LANG_FRENCH
#define LANG_FRENCH (0)
#endif
#ifndef LANG_FRISIAN
#define LANG_FRISIAN (0)
#endif
#ifndef LANG_GEORGIAN
#define LANG_GEORGIAN (0)
#endif
#ifndef LANG_GERMAN
#define LANG_GERMAN (0)
#endif
#ifndef LANG_GREEK
#define LANG_GREEK (0)
#endif
#ifndef LANG_GUJARATI
#define LANG_GUJARATI (0)
#endif
#ifndef LANG_HEBREW
#define LANG_HEBREW (0)
#endif
#ifndef LANG_HINDI
#define LANG_HINDI (0)
#endif
#ifndef LANG_HUNGARIAN
#define LANG_HUNGARIAN (0)
#endif
#ifndef LANG_ICELANDIC
#define LANG_ICELANDIC (0)
#endif
#ifndef LANG_INDONESIAN
#define LANG_INDONESIAN (0)
#endif
#ifndef LANG_ITALIAN
#define LANG_ITALIAN (0)
#endif
#ifndef LANG_JAPANESE
#define LANG_JAPANESE (0)
#endif
#ifndef LANG_KABYLE
#define LANG_KABYLE (0)
#endif
#ifndef LANG_KANNADA
#define LANG_KANNADA (0)
#endif
#ifndef LANG_KASHMIRI
#define LANG_KASHMIRI (0)
#endif
#ifndef LANG_KAZAK
#define LANG_KAZAK (0)
#endif
#ifndef LANG_KONKANI
#define LANG_KONKANI (0)
#endif
#ifndef LANG_KOREAN
#define LANG_KOREAN (0)
#endif
#ifndef LANG_LATVIAN
#define LANG_LATVIAN (0)
#endif
#ifndef LANG_LITHUANIAN
#define LANG_LITHUANIAN (0)
#endif
#ifndef LANG_MACEDONIAN
#define LANG_MACEDONIAN (0)
#endif
#ifndef LANG_MALAY
#define LANG_MALAY (0)
#endif
#ifndef LANG_MALAYALAM
#define LANG_MALAYALAM (0)
#endif
#ifndef LANG_MANIPURI
#define LANG_MANIPURI (0)
#endif
#ifndef LANG_MARATHI
#define LANG_MARATHI (0)
#endif
#ifndef LANG_NEPALI
#define LANG_NEPALI (0)
#endif
#ifndef LANG_NORWEGIAN
#define LANG_NORWEGIAN (0)
#endif
#ifndef LANG_ORIYA
#define LANG_ORIYA (0)
#endif
#ifndef LANG_POLISH
#define LANG_POLISH (0)
#endif
#ifndef LANG_PORTUGUESE
#define LANG_PORTUGUESE (0)
#endif
#ifndef LANG_PUNJABI
#define LANG_PUNJABI (0)
#endif
#ifndef LANG_ROMANIAN
#define LANG_ROMANIAN (0)
#endif
#ifndef LANG_RUSSIAN
#define LANG_RUSSIAN (0)
#endif
#ifndef LANG_SAMI
#define LANG_SAMI (0)
#endif
#ifndef LANG_SANSKRIT
#define LANG_SANSKRIT (0)
#endif
#ifndef LANG_SERBIAN
#define LANG_SERBIAN (0)
#endif
#ifndef LANG_SINDHI
#define LANG_SINDHI (0)
#endif
#ifndef LANG_SLOVAK
#define LANG_SLOVAK (0)
#endif
#ifndef LANG_SLOVENIAN
#define LANG_SLOVENIAN (0)
#endif
#ifndef LANG_SPANISH
#define LANG_SPANISH (0)
#endif
#ifndef LANG_SWAHILI
#define LANG_SWAHILI (0)
#endif
#ifndef LANG_SWEDISH
#define LANG_SWEDISH (0)
#endif
#ifndef LANG_TAMIL
#define LANG_TAMIL (0)
#endif
#ifndef LANG_TATAR
#define LANG_TATAR (0)
#endif
#ifndef LANG_TELUGU
#define LANG_TELUGU (0)
#endif
#ifndef LANG_THAI
#define LANG_THAI (0)
#endif
#ifndef LANG_TURKISH
#define LANG_TURKISH (0)
#endif
#ifndef LANG_UKRAINIAN
#define LANG_UKRAINIAN (0)
#endif
#ifndef LANG_URDU
#define LANG_URDU (0)
#endif
#ifndef LANG_UZBEK
#define LANG_UZBEK (0)
#endif
#ifndef LANG_VIETNAMESE
#define LANG_VIETNAMESE (0)
#endif
#ifndef SUBLANG_ARABIC_ALGERIA
#define SUBLANG_ARABIC_ALGERIA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_BAHRAIN
#define SUBLANG_ARABIC_BAHRAIN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_EGYPT
#define SUBLANG_ARABIC_EGYPT SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_IRAQ
#define SUBLANG_ARABIC_IRAQ SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_JORDAN
#define SUBLANG_ARABIC_JORDAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_KUWAIT
#define SUBLANG_ARABIC_KUWAIT SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_LEBANON
#define SUBLANG_ARABIC_LEBANON SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_LIBYA
#define SUBLANG_ARABIC_LIBYA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_MOROCCO
#define SUBLANG_ARABIC_MOROCCO SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_OMAN
#define SUBLANG_ARABIC_OMAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_QATAR
#define SUBLANG_ARABIC_QATAR SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_SAUDI_ARABIA
#define SUBLANG_ARABIC_SAUDI_ARABIA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_SYRIA
#define SUBLANG_ARABIC_SYRIA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_TUNISIA
#define SUBLANG_ARABIC_TUNISIA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_UAE
#define SUBLANG_ARABIC_UAE SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ARABIC_YEMEN
#define SUBLANG_ARABIC_YEMEN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_AZERI_CYRILLIC
#define SUBLANG_AZERI_CYRILLIC SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_AZERI_LATIN
#define SUBLANG_AZERI_LATIN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_BOSNIAN_BOSNIA_HERZEGOVINA_LATIN
#define SUBLANG_BOSNIAN_BOSNIA_HERZEGOVINA_LATIN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_CHINESE_SIMPLIFIED
#define SUBLANG_CHINESE_SIMPLIFIED SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_CHINESE_TRADITIONAL
#define SUBLANG_CHINESE_TRADITIONAL SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_CHINESE_HONGKONG
#define SUBLANG_CHINESE_HONGKONG SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_CHINESE_MACAU
#define SUBLANG_CHINESE_MACAU SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_CHINESE_SINGAPORE
#define SUBLANG_CHINESE_SINGAPORE SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_DUTCH
#define SUBLANG_DUTCH SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_DUTCH_BELGIAN
#define SUBLANG_DUTCH_BELGIAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_UK
#define SUBLANG_ENGLISH_UK SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_US
#define SUBLANG_ENGLISH_US SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_AUS
#define SUBLANG_ENGLISH_AUS SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_BELIZE
#define SUBLANG_ENGLISH_BELIZE SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_CAN
#define SUBLANG_ENGLISH_CAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_CARIBBEAN
#define SUBLANG_ENGLISH_CARIBBEAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_EIRE
#define SUBLANG_ENGLISH_EIRE SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_JAMAICA
#define SUBLANG_ENGLISH_JAMAICA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_NZ
#define SUBLANG_ENGLISH_NZ SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_PHILIPPINES
#define SUBLANG_ENGLISH_PHILIPPINES SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_SOUTH_AFRICA
#define SUBLANG_ENGLISH_SOUTH_AFRICA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_TRINIDAD
#define SUBLANG_ENGLISH_TRINIDAD SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ENGLISH_ZIMBABWE
#define SUBLANG_ENGLISH_ZIMBABWE SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_FRENCH
#define SUBLANG_FRENCH SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_FRENCH_BELGIAN
#define SUBLANG_FRENCH_BELGIAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_FRENCH_CANADIAN
#define SUBLANG_FRENCH_CANADIAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_FRENCH_LUXEMBOURG
#define SUBLANG_FRENCH_LUXEMBOURG SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_FRENCH_MONACO
#define SUBLANG_FRENCH_MONACO SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_FRENCH_SWISS
#define SUBLANG_FRENCH_SWISS SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_GERMAN
#define SUBLANG_GERMAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_GERMAN_AUSTRIAN
#define SUBLANG_GERMAN_AUSTRIAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_GERMAN_LIECHTENSTEIN
#define SUBLANG_GERMAN_LIECHTENSTEIN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_GERMAN_LUXEMBOURG
#define SUBLANG_GERMAN_LUXEMBOURG SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_GERMAN_SWISS
#define SUBLANG_GERMAN_SWISS SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ITALIAN
#define SUBLANG_ITALIAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_ITALIAN_SWISS
#define SUBLANG_ITALIAN_SWISS SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_KASHMIRI_INDIA
#define SUBLANG_KASHMIRI_INDIA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_KOREAN
#define SUBLANG_KOREAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_LITHUANIAN
#define SUBLANG_LITHUANIAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_MALAY_BRUNEI_DARUSSALAM
#define SUBLANG_MALAY_BRUNEI_DARUSSALAM SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_MALAY_MALAYSIA
#define SUBLANG_MALAY_MALAYSIA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_NEPALI_INDIA
#define SUBLANG_NEPALI_INDIA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_NORWEGIAN_BOKMAL
#define SUBLANG_NORWEGIAN_BOKMAL SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_NORWEGIAN_NYNORSK
#define SUBLANG_NORWEGIAN_NYNORSK SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_PORTUGUESE
#define SUBLANG_PORTUGUESE SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_PORTUGUESE_BRAZILIAN
#define SUBLANG_PORTUGUESE_BRAZILIAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SERBIAN_CYRILLIC
#define SUBLANG_SERBIAN_CYRILLIC SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SERBIAN_LATIN
#define SUBLANG_SERBIAN_LATIN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH
#define SUBLANG_SPANISH SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_ARGENTINA
#define SUBLANG_SPANISH_ARGENTINA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_BOLIVIA
#define SUBLANG_SPANISH_BOLIVIA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_CHILE
#define SUBLANG_SPANISH_CHILE SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_COLOMBIA
#define SUBLANG_SPANISH_COLOMBIA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_COSTA_RICA
#define SUBLANG_SPANISH_COSTA_RICA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_DOMINICAN_REPUBLIC
#define SUBLANG_SPANISH_DOMINICAN_REPUBLIC SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_ECUADOR
#define SUBLANG_SPANISH_ECUADOR SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_EL_SALVADOR
#define SUBLANG_SPANISH_EL_SALVADOR SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_GUATEMALA
#define SUBLANG_SPANISH_GUATEMALA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_HONDURAS
#define SUBLANG_SPANISH_HONDURAS SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_MEXICAN
#define SUBLANG_SPANISH_MEXICAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_MODERN
#define SUBLANG_SPANISH_MODERN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_NICARAGUA
#define SUBLANG_SPANISH_NICARAGUA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_PANAMA
#define SUBLANG_SPANISH_PANAMA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_PARAGUAY
#define SUBLANG_SPANISH_PARAGUAY SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_PERU
#define SUBLANG_SPANISH_PERU SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_PUERTO_RICO
#define SUBLANG_SPANISH_PUERTO_RICO SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_URUGUAY
#define SUBLANG_SPANISH_URUGUAY SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SPANISH_VENEZUELA
#define SUBLANG_SPANISH_VENEZUELA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SWEDISH
#define SUBLANG_SWEDISH SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_SWEDISH_FINLAND
#define SUBLANG_SWEDISH_FINLAND SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_URDU_INDIA
#define SUBLANG_URDU_INDIA SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_URDU_PAKISTAN
#define SUBLANG_URDU_PAKISTAN SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_UZBEK_CYRILLIC
#define SUBLANG_UZBEK_CYRILLIC SUBLANG_DEFAULT
#endif
#ifndef SUBLANG_UZBEK_LATIN
#define SUBLANG_UZBEK_LATIN SUBLANG_DEFAULT
#endif


#endif 
#define LNG(wxlang, canonical, winlang, winsublang, layout, desc) \
    info.Language = wxlang;                               \
    info.CanonicalName = wxT(canonical);                  \
    info.LayoutDirection = layout;                        \
    info.Description = wxT(desc);                         \
    SETWINLANG(info, winlang, winsublang)                 \
    AddLanguage(info);

void wxLocale::InitLanguagesDB()
{
   wxLanguageInfo info;

   LNG(wxLANGUAGE_ABKHAZIAN,                  "ab"   , 0              , 0                                 , wxLayout_LeftToRight, "Abkhazian")
   LNG(wxLANGUAGE_AFAR,                       "aa"   , 0              , 0                                 , wxLayout_LeftToRight, "Afar")
   LNG(wxLANGUAGE_AFRIKAANS,                  "af_ZA", LANG_AFRIKAANS , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Afrikaans")
   LNG(wxLANGUAGE_ALBANIAN,                   "sq_AL", LANG_ALBANIAN  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Albanian")
   LNG(wxLANGUAGE_AMHARIC,                    "am"   , 0              , 0                                 , wxLayout_LeftToRight, "Amharic")
   LNG(wxLANGUAGE_ARABIC,                     "ar"   , LANG_ARABIC    , SUBLANG_DEFAULT                   , wxLayout_RightToLeft, "Arabic")
   LNG(wxLANGUAGE_ARABIC_ALGERIA,             "ar_DZ", LANG_ARABIC    , SUBLANG_ARABIC_ALGERIA            , wxLayout_RightToLeft, "Arabic (Algeria)")
   LNG(wxLANGUAGE_ARABIC_BAHRAIN,             "ar_BH", LANG_ARABIC    , SUBLANG_ARABIC_BAHRAIN            , wxLayout_RightToLeft, "Arabic (Bahrain)")
   LNG(wxLANGUAGE_ARABIC_EGYPT,               "ar_EG", LANG_ARABIC    , SUBLANG_ARABIC_EGYPT              , wxLayout_RightToLeft, "Arabic (Egypt)")
   LNG(wxLANGUAGE_ARABIC_IRAQ,                "ar_IQ", LANG_ARABIC    , SUBLANG_ARABIC_IRAQ               , wxLayout_RightToLeft, "Arabic (Iraq)")
   LNG(wxLANGUAGE_ARABIC_JORDAN,              "ar_JO", LANG_ARABIC    , SUBLANG_ARABIC_JORDAN             , wxLayout_RightToLeft, "Arabic (Jordan)")
   LNG(wxLANGUAGE_ARABIC_KUWAIT,              "ar_KW", LANG_ARABIC    , SUBLANG_ARABIC_KUWAIT             , wxLayout_RightToLeft, "Arabic (Kuwait)")
   LNG(wxLANGUAGE_ARABIC_LEBANON,             "ar_LB", LANG_ARABIC    , SUBLANG_ARABIC_LEBANON            , wxLayout_RightToLeft, "Arabic (Lebanon)")
   LNG(wxLANGUAGE_ARABIC_LIBYA,               "ar_LY", LANG_ARABIC    , SUBLANG_ARABIC_LIBYA              , wxLayout_RightToLeft, "Arabic (Libya)")
   LNG(wxLANGUAGE_ARABIC_MOROCCO,             "ar_MA", LANG_ARABIC    , SUBLANG_ARABIC_MOROCCO            , wxLayout_RightToLeft, "Arabic (Morocco)")
   LNG(wxLANGUAGE_ARABIC_OMAN,                "ar_OM", LANG_ARABIC    , SUBLANG_ARABIC_OMAN               , wxLayout_RightToLeft, "Arabic (Oman)")
   LNG(wxLANGUAGE_ARABIC_QATAR,               "ar_QA", LANG_ARABIC    , SUBLANG_ARABIC_QATAR              , wxLayout_RightToLeft, "Arabic (Qatar)")
   LNG(wxLANGUAGE_ARABIC_SAUDI_ARABIA,        "ar_SA", LANG_ARABIC    , SUBLANG_ARABIC_SAUDI_ARABIA       , wxLayout_RightToLeft, "Arabic (Saudi Arabia)")
   LNG(wxLANGUAGE_ARABIC_SUDAN,               "ar_SD", 0              , 0                                 , wxLayout_RightToLeft, "Arabic (Sudan)")
   LNG(wxLANGUAGE_ARABIC_SYRIA,               "ar_SY", LANG_ARABIC    , SUBLANG_ARABIC_SYRIA              , wxLayout_RightToLeft, "Arabic (Syria)")
   LNG(wxLANGUAGE_ARABIC_TUNISIA,             "ar_TN", LANG_ARABIC    , SUBLANG_ARABIC_TUNISIA            , wxLayout_RightToLeft, "Arabic (Tunisia)")
   LNG(wxLANGUAGE_ARABIC_UAE,                 "ar_AE", LANG_ARABIC    , SUBLANG_ARABIC_UAE                , wxLayout_RightToLeft, "Arabic (Uae)")
   LNG(wxLANGUAGE_ARABIC_YEMEN,               "ar_YE", LANG_ARABIC    , SUBLANG_ARABIC_YEMEN              , wxLayout_RightToLeft, "Arabic (Yemen)")
   LNG(wxLANGUAGE_ARMENIAN,                   "hy"   , LANG_ARMENIAN  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Armenian")
   LNG(wxLANGUAGE_ASSAMESE,                   "as"   , LANG_ASSAMESE  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Assamese")
   LNG(wxLANGUAGE_ASTURIAN,                   "ast"  , 0              , 0                                 , wxLayout_LeftToRight, "Asturian")
   LNG(wxLANGUAGE_AYMARA,                     "ay"   , 0              , 0                                 , wxLayout_LeftToRight, "Aymara")
   LNG(wxLANGUAGE_AZERI,                      "az"   , LANG_AZERI     , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Azeri")
   LNG(wxLANGUAGE_AZERI_CYRILLIC,             "az"   , LANG_AZERI     , SUBLANG_AZERI_CYRILLIC            , wxLayout_LeftToRight, "Azeri (Cyrillic)")
   LNG(wxLANGUAGE_AZERI_LATIN,                "az"   , LANG_AZERI     , SUBLANG_AZERI_LATIN               , wxLayout_LeftToRight, "Azeri (Latin)")
   LNG(wxLANGUAGE_BASHKIR,                    "ba"   , 0              , 0                                 , wxLayout_LeftToRight, "Bashkir")
   LNG(wxLANGUAGE_BASQUE,                     "eu_ES", LANG_BASQUE    , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Basque")
   LNG(wxLANGUAGE_BELARUSIAN,                 "be_BY", LANG_BELARUSIAN, SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Belarusian")
   LNG(wxLANGUAGE_BENGALI,                    "bn"   , LANG_BENGALI   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Bengali")
   LNG(wxLANGUAGE_BHUTANI,                    "dz"   , 0              , 0                                 , wxLayout_LeftToRight, "Bhutani")
   LNG(wxLANGUAGE_BIHARI,                     "bh"   , 0              , 0                                 , wxLayout_LeftToRight, "Bihari")
   LNG(wxLANGUAGE_BISLAMA,                    "bi"   , 0              , 0                                 , wxLayout_LeftToRight, "Bislama")
   LNG(wxLANGUAGE_BOSNIAN,                    "bs"   , LANG_BOSNIAN   , SUBLANG_BOSNIAN_BOSNIA_HERZEGOVINA_LATIN, wxLayout_LeftToRight, "Bosnian")
   LNG(wxLANGUAGE_BRETON,                     "br"   , 0              , 0                                 , wxLayout_LeftToRight, "Breton")
   LNG(wxLANGUAGE_BULGARIAN,                  "bg_BG", LANG_BULGARIAN , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Bulgarian")
   LNG(wxLANGUAGE_BURMESE,                    "my"   , 0              , 0                                 , wxLayout_LeftToRight, "Burmese")
   LNG(wxLANGUAGE_CATALAN,                    "ca_ES", LANG_CATALAN   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Catalan")
   LNG(wxLANGUAGE_CHINESE,                    "zh_TW", LANG_CHINESE   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Chinese")
   LNG(wxLANGUAGE_CHINESE_SIMPLIFIED,         "zh_CN", LANG_CHINESE   , SUBLANG_CHINESE_SIMPLIFIED        , wxLayout_LeftToRight, "Chinese (Simplified)")
   LNG(wxLANGUAGE_CHINESE_TRADITIONAL,        "zh_TW", LANG_CHINESE   , SUBLANG_CHINESE_TRADITIONAL       , wxLayout_LeftToRight, "Chinese (Traditional)")
   LNG(wxLANGUAGE_CHINESE_HONGKONG,           "zh_HK", LANG_CHINESE   , SUBLANG_CHINESE_HONGKONG          , wxLayout_LeftToRight, "Chinese (Hongkong)")
   LNG(wxLANGUAGE_CHINESE_MACAU,              "zh_MO", LANG_CHINESE   , SUBLANG_CHINESE_MACAU             , wxLayout_LeftToRight, "Chinese (Macau)")
   LNG(wxLANGUAGE_CHINESE_SINGAPORE,          "zh_SG", LANG_CHINESE   , SUBLANG_CHINESE_SINGAPORE         , wxLayout_LeftToRight, "Chinese (Singapore)")
   LNG(wxLANGUAGE_CHINESE_TAIWAN,             "zh_TW", LANG_CHINESE   , SUBLANG_CHINESE_TRADITIONAL       , wxLayout_LeftToRight, "Chinese (Taiwan)")
   LNG(wxLANGUAGE_CORSICAN,                   "co"   , 0              , 0                                 , wxLayout_LeftToRight, "Corsican")
   LNG(wxLANGUAGE_CROATIAN,                   "hr_HR", LANG_CROATIAN  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Croatian")
   LNG(wxLANGUAGE_CZECH,                      "cs_CZ", LANG_CZECH     , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Czech")
   LNG(wxLANGUAGE_DANISH,                     "da_DK", LANG_DANISH    , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Danish")
   LNG(wxLANGUAGE_DUTCH,                      "nl_NL", LANG_DUTCH     , SUBLANG_DUTCH                     , wxLayout_LeftToRight, "Dutch")
   LNG(wxLANGUAGE_DUTCH_BELGIAN,              "nl_BE", LANG_DUTCH     , SUBLANG_DUTCH_BELGIAN             , wxLayout_LeftToRight, "Dutch (Belgian)")
   LNG(wxLANGUAGE_ENGLISH,                    "en_GB", LANG_ENGLISH   , SUBLANG_ENGLISH_UK                , wxLayout_LeftToRight, "English")
   LNG(wxLANGUAGE_ENGLISH_UK,                 "en_GB", LANG_ENGLISH   , SUBLANG_ENGLISH_UK                , wxLayout_LeftToRight, "English (U.K.)")
   LNG(wxLANGUAGE_ENGLISH_US,                 "en_US", LANG_ENGLISH   , SUBLANG_ENGLISH_US                , wxLayout_LeftToRight, "English (U.S.)")
   LNG(wxLANGUAGE_ENGLISH_AUSTRALIA,          "en_AU", LANG_ENGLISH   , SUBLANG_ENGLISH_AUS               , wxLayout_LeftToRight, "English (Australia)")
   LNG(wxLANGUAGE_ENGLISH_BELIZE,             "en_BZ", LANG_ENGLISH   , SUBLANG_ENGLISH_BELIZE            , wxLayout_LeftToRight, "English (Belize)")
   LNG(wxLANGUAGE_ENGLISH_BOTSWANA,           "en_BW", 0              , 0                                 , wxLayout_LeftToRight, "English (Botswana)")
   LNG(wxLANGUAGE_ENGLISH_CANADA,             "en_CA", LANG_ENGLISH   , SUBLANG_ENGLISH_CAN               , wxLayout_LeftToRight, "English (Canada)")
   LNG(wxLANGUAGE_ENGLISH_CARIBBEAN,          "en_CB", LANG_ENGLISH   , SUBLANG_ENGLISH_CARIBBEAN         , wxLayout_LeftToRight, "English (Caribbean)")
   LNG(wxLANGUAGE_ENGLISH_DENMARK,            "en_DK", 0              , 0                                 , wxLayout_LeftToRight, "English (Denmark)")
   LNG(wxLANGUAGE_ENGLISH_EIRE,               "en_IE", LANG_ENGLISH   , SUBLANG_ENGLISH_EIRE              , wxLayout_LeftToRight, "English (Eire)")
   LNG(wxLANGUAGE_ENGLISH_JAMAICA,            "en_JM", LANG_ENGLISH   , SUBLANG_ENGLISH_JAMAICA           , wxLayout_LeftToRight, "English (Jamaica)")
   LNG(wxLANGUAGE_ENGLISH_NEW_ZEALAND,        "en_NZ", LANG_ENGLISH   , SUBLANG_ENGLISH_NZ                , wxLayout_LeftToRight, "English (New Zealand)")
   LNG(wxLANGUAGE_ENGLISH_PHILIPPINES,        "en_PH", LANG_ENGLISH   , SUBLANG_ENGLISH_PHILIPPINES       , wxLayout_LeftToRight, "English (Philippines)")
   LNG(wxLANGUAGE_ENGLISH_SOUTH_AFRICA,       "en_ZA", LANG_ENGLISH   , SUBLANG_ENGLISH_SOUTH_AFRICA      , wxLayout_LeftToRight, "English (South Africa)")
   LNG(wxLANGUAGE_ENGLISH_TRINIDAD,           "en_TT", LANG_ENGLISH   , SUBLANG_ENGLISH_TRINIDAD          , wxLayout_LeftToRight, "English (Trinidad)")
   LNG(wxLANGUAGE_ENGLISH_ZIMBABWE,           "en_ZW", LANG_ENGLISH   , SUBLANG_ENGLISH_ZIMBABWE          , wxLayout_LeftToRight, "English (Zimbabwe)")
   LNG(wxLANGUAGE_ESPERANTO,                  "eo"   , 0              , 0                                 , wxLayout_LeftToRight, "Esperanto")
   LNG(wxLANGUAGE_ESTONIAN,                   "et_EE", LANG_ESTONIAN  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Estonian")
   LNG(wxLANGUAGE_FAEROESE,                   "fo_FO", LANG_FAEROESE  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Faeroese")
   LNG(wxLANGUAGE_FARSI,                      "fa_IR", LANG_FARSI     , SUBLANG_DEFAULT                   , wxLayout_RightToLeft, "Farsi")
   LNG(wxLANGUAGE_FIJI,                       "fj"   , 0              , 0                                 , wxLayout_LeftToRight, "Fiji")
   LNG(wxLANGUAGE_FINNISH,                    "fi_FI", LANG_FINNISH   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Finnish")
   LNG(wxLANGUAGE_FRENCH,                     "fr_FR", LANG_FRENCH    , SUBLANG_FRENCH                    , wxLayout_LeftToRight, "French")
   LNG(wxLANGUAGE_FRENCH_BELGIAN,             "fr_BE", LANG_FRENCH    , SUBLANG_FRENCH_BELGIAN            , wxLayout_LeftToRight, "French (Belgian)")
   LNG(wxLANGUAGE_FRENCH_CANADIAN,            "fr_CA", LANG_FRENCH    , SUBLANG_FRENCH_CANADIAN           , wxLayout_LeftToRight, "French (Canadian)")
   LNG(wxLANGUAGE_FRENCH_LUXEMBOURG,          "fr_LU", LANG_FRENCH    , SUBLANG_FRENCH_LUXEMBOURG         , wxLayout_LeftToRight, "French (Luxembourg)")
   LNG(wxLANGUAGE_FRENCH_MONACO,              "fr_MC", LANG_FRENCH    , SUBLANG_FRENCH_MONACO             , wxLayout_LeftToRight, "French (Monaco)")
   LNG(wxLANGUAGE_FRENCH_SWISS,               "fr_CH", LANG_FRENCH    , SUBLANG_FRENCH_SWISS              , wxLayout_LeftToRight, "French (Swiss)")
   LNG(wxLANGUAGE_FRISIAN,                    "fy"   , LANG_FRISIAN   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Frisian")
   LNG(wxLANGUAGE_GALICIAN,                   "gl_ES", 0              , 0                                 , wxLayout_LeftToRight, "Galician")
   LNG(wxLANGUAGE_GEORGIAN,                   "ka_GE", LANG_GEORGIAN  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Georgian")
   LNG(wxLANGUAGE_GERMAN,                     "de_DE", LANG_GERMAN    , SUBLANG_GERMAN                    , wxLayout_LeftToRight, "German")
   LNG(wxLANGUAGE_GERMAN_AUSTRIAN,            "de_AT", LANG_GERMAN    , SUBLANG_GERMAN_AUSTRIAN           , wxLayout_LeftToRight, "German (Austrian)")
   LNG(wxLANGUAGE_GERMAN_BELGIUM,             "de_BE", 0              , 0                                 , wxLayout_LeftToRight, "German (Belgium)")
   LNG(wxLANGUAGE_GERMAN_LIECHTENSTEIN,       "de_LI", LANG_GERMAN    , SUBLANG_GERMAN_LIECHTENSTEIN      , wxLayout_LeftToRight, "German (Liechtenstein)")
   LNG(wxLANGUAGE_GERMAN_LUXEMBOURG,          "de_LU", LANG_GERMAN    , SUBLANG_GERMAN_LUXEMBOURG         , wxLayout_LeftToRight, "German (Luxembourg)")
   LNG(wxLANGUAGE_GERMAN_SWISS,               "de_CH", LANG_GERMAN    , SUBLANG_GERMAN_SWISS              , wxLayout_LeftToRight, "German (Swiss)")
   LNG(wxLANGUAGE_GREEK,                      "el_GR", LANG_GREEK     , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Greek")
   LNG(wxLANGUAGE_GREENLANDIC,                "kl_GL", 0              , 0                                 , wxLayout_LeftToRight, "Greenlandic")
   LNG(wxLANGUAGE_GUARANI,                    "gn"   , 0              , 0                                 , wxLayout_LeftToRight, "Guarani")
   LNG(wxLANGUAGE_GUJARATI,                   "gu"   , LANG_GUJARATI  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Gujarati")
   LNG(wxLANGUAGE_HAUSA,                      "ha"   , 0              , 0                                 , wxLayout_LeftToRight, "Hausa")
   LNG(wxLANGUAGE_HEBREW,                     "he_IL", LANG_HEBREW    , SUBLANG_DEFAULT                   , wxLayout_RightToLeft, "Hebrew")
   LNG(wxLANGUAGE_HINDI,                      "hi_IN", LANG_HINDI     , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Hindi")
   LNG(wxLANGUAGE_HUNGARIAN,                  "hu_HU", LANG_HUNGARIAN , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Hungarian")
   LNG(wxLANGUAGE_ICELANDIC,                  "is_IS", LANG_ICELANDIC , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Icelandic")
   LNG(wxLANGUAGE_INDONESIAN,                 "id_ID", LANG_INDONESIAN, SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Indonesian")
   LNG(wxLANGUAGE_INTERLINGUA,                "ia"   , 0              , 0                                 , wxLayout_LeftToRight, "Interlingua")
   LNG(wxLANGUAGE_INTERLINGUE,                "ie"   , 0              , 0                                 , wxLayout_LeftToRight, "Interlingue")
   LNG(wxLANGUAGE_INUKTITUT,                  "iu"   , 0              , 0                                 , wxLayout_LeftToRight, "Inuktitut")
   LNG(wxLANGUAGE_INUPIAK,                    "ik"   , 0              , 0                                 , wxLayout_LeftToRight, "Inupiak")
   LNG(wxLANGUAGE_IRISH,                      "ga_IE", 0              , 0                                 , wxLayout_LeftToRight, "Irish")
   LNG(wxLANGUAGE_ITALIAN,                    "it_IT", LANG_ITALIAN   , SUBLANG_ITALIAN                   , wxLayout_LeftToRight, "Italian")
   LNG(wxLANGUAGE_ITALIAN_SWISS,              "it_CH", LANG_ITALIAN   , SUBLANG_ITALIAN_SWISS             , wxLayout_LeftToRight, "Italian (Swiss)")
   LNG(wxLANGUAGE_JAPANESE,                   "ja_JP", LANG_JAPANESE  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Japanese")
   LNG(wxLANGUAGE_JAVANESE,                   "jv"   , 0              , 0                                 , wxLayout_LeftToRight, "Javanese")
   LNG(wxLANGUAGE_KABYLE,                     "kab"  , LANG_KABYLE    , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Kabyle")
   LNG(wxLANGUAGE_KANNADA,                    "kn"   , LANG_KANNADA   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Kannada")
   LNG(wxLANGUAGE_KASHMIRI,                   "ks"   , LANG_KASHMIRI  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Kashmiri")
   LNG(wxLANGUAGE_KASHMIRI_INDIA,             "ks_IN", LANG_KASHMIRI  , SUBLANG_KASHMIRI_INDIA            , wxLayout_LeftToRight, "Kashmiri (India)")
   LNG(wxLANGUAGE_KAZAKH,                     "kk"   , LANG_KAZAK     , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Kazakh")
   LNG(wxLANGUAGE_KERNEWEK,                   "kw_GB", 0              , 0                                 , wxLayout_LeftToRight, "Kernewek")
   LNG(wxLANGUAGE_KHMER,                      "km"   , 0              , 0                                 , wxLayout_LeftToRight, "Khmer")
   LNG(wxLANGUAGE_KINYARWANDA,                "rw"   , 0              , 0                                 , wxLayout_LeftToRight, "Kinyarwanda")
   LNG(wxLANGUAGE_KIRGHIZ,                    "ky"   , 0              , 0                                 , wxLayout_LeftToRight, "Kirghiz")
   LNG(wxLANGUAGE_KIRUNDI,                    "rn"   , 0              , 0                                 , wxLayout_LeftToRight, "Kirundi")
   LNG(wxLANGUAGE_KONKANI,                    ""     , LANG_KONKANI   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Konkani")
   LNG(wxLANGUAGE_KOREAN,                     "ko_KR", LANG_KOREAN    , SUBLANG_KOREAN                    , wxLayout_LeftToRight, "Korean")
   LNG(wxLANGUAGE_KURDISH,                    "ku_TR", 0              , 0                                 , wxLayout_LeftToRight, "Kurdish")
   LNG(wxLANGUAGE_LAOTHIAN,                   "lo"   , 0              , 0                                 , wxLayout_LeftToRight, "Laothian")
   LNG(wxLANGUAGE_LATIN,                      "la"   , 0              , 0                                 , wxLayout_LeftToRight, "Latin")
   LNG(wxLANGUAGE_LATVIAN,                    "lv_LV", LANG_LATVIAN   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Latvian")
   LNG(wxLANGUAGE_LINGALA,                    "ln"   , 0              , 0                                 , wxLayout_LeftToRight, "Lingala")
   LNG(wxLANGUAGE_LITHUANIAN,                 "lt_LT", LANG_LITHUANIAN, SUBLANG_LITHUANIAN                , wxLayout_LeftToRight, "Lithuanian")
   LNG(wxLANGUAGE_MACEDONIAN,                 "mk_MK", LANG_MACEDONIAN, SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Macedonian")
   LNG(wxLANGUAGE_MALAGASY,                   "mg"   , 0              , 0                                 , wxLayout_LeftToRight, "Malagasy")
   LNG(wxLANGUAGE_MALAY,                      "ms_MY", LANG_MALAY     , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Malay")
   LNG(wxLANGUAGE_MALAYALAM,                  "ml"   , LANG_MALAYALAM , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Malayalam")
   LNG(wxLANGUAGE_MALAY_BRUNEI_DARUSSALAM,    "ms_BN", LANG_MALAY     , SUBLANG_MALAY_BRUNEI_DARUSSALAM   , wxLayout_LeftToRight, "Malay (Brunei Darussalam)")
   LNG(wxLANGUAGE_MALAY_MALAYSIA,             "ms_MY", LANG_MALAY     , SUBLANG_MALAY_MALAYSIA            , wxLayout_LeftToRight, "Malay (Malaysia)")
   LNG(wxLANGUAGE_MALTESE,                    "mt_MT", 0              , 0                                 , wxLayout_LeftToRight, "Maltese")
   LNG(wxLANGUAGE_MANIPURI,                   ""     , LANG_MANIPURI  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Manipuri")
   LNG(wxLANGUAGE_MAORI,                      "mi"   , 0              , 0                                 , wxLayout_LeftToRight, "Maori")
   LNG(wxLANGUAGE_MARATHI,                    "mr_IN", LANG_MARATHI   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Marathi")
   LNG(wxLANGUAGE_MOLDAVIAN,                  "mo"   , 0              , 0                                 , wxLayout_LeftToRight, "Moldavian")
   LNG(wxLANGUAGE_MONGOLIAN,                  "mn"   , 0              , 0                                 , wxLayout_LeftToRight, "Mongolian")
   LNG(wxLANGUAGE_NAURU,                      "na"   , 0              , 0                                 , wxLayout_LeftToRight, "Nauru")
   LNG(wxLANGUAGE_NEPALI,                     "ne_NP", LANG_NEPALI    , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Nepali")
   LNG(wxLANGUAGE_NEPALI_INDIA,               "ne_IN", LANG_NEPALI    , SUBLANG_NEPALI_INDIA              , wxLayout_LeftToRight, "Nepali (India)")
   LNG(wxLANGUAGE_NORWEGIAN_BOKMAL,           "nb_NO", LANG_NORWEGIAN , SUBLANG_NORWEGIAN_BOKMAL          , wxLayout_LeftToRight, "Norwegian (Bokmal)")
   LNG(wxLANGUAGE_NORWEGIAN_NYNORSK,          "nn_NO", LANG_NORWEGIAN , SUBLANG_NORWEGIAN_NYNORSK         , wxLayout_LeftToRight, "Norwegian (Nynorsk)")
   LNG(wxLANGUAGE_OCCITAN,                    "oc"   , 0              , 0                                 , wxLayout_LeftToRight, "Occitan")
   LNG(wxLANGUAGE_ORIYA,                      "or"   , LANG_ORIYA     , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Oriya")
   LNG(wxLANGUAGE_OROMO,                      "om"   , 0              , 0                                 , wxLayout_LeftToRight, "(Afan) Oromo")
   LNG(wxLANGUAGE_PASHTO,                     "ps"   , 0              , 0                                 , wxLayout_LeftToRight, "Pashto, Pushto")
   LNG(wxLANGUAGE_POLISH,                     "pl_PL", LANG_POLISH    , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Polish")
   LNG(wxLANGUAGE_PORTUGUESE,                 "pt_PT", LANG_PORTUGUESE, SUBLANG_PORTUGUESE                , wxLayout_LeftToRight, "Portuguese")
   LNG(wxLANGUAGE_PORTUGUESE_BRAZILIAN,       "pt_BR", LANG_PORTUGUESE, SUBLANG_PORTUGUESE_BRAZILIAN      , wxLayout_LeftToRight, "Portuguese (Brazilian)")
   LNG(wxLANGUAGE_PUNJABI,                    "pa"   , LANG_PUNJABI   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Punjabi")
   LNG(wxLANGUAGE_QUECHUA,                    "qu"   , 0              , 0                                 , wxLayout_LeftToRight, "Quechua")
   LNG(wxLANGUAGE_RHAETO_ROMANCE,             "rm"   , 0              , 0                                 , wxLayout_LeftToRight, "Rhaeto-Romance")
   LNG(wxLANGUAGE_ROMANIAN,                   "ro_RO", LANG_ROMANIAN  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Romanian")
   LNG(wxLANGUAGE_RUSSIAN,                    "ru_RU", LANG_RUSSIAN   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Russian")
   LNG(wxLANGUAGE_RUSSIAN_UKRAINE,            "ru_UA", 0              , 0                                 , wxLayout_LeftToRight, "Russian (Ukraine)")
   LNG(wxLANGUAGE_SAMI,                       "se_NO", LANG_SAMI      , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Northern Sami")
   LNG(wxLANGUAGE_SAMOAN,                     "sm"   , 0              , 0                                 , wxLayout_LeftToRight, "Samoan")
   LNG(wxLANGUAGE_SANGHO,                     "sg"   , 0              , 0                                 , wxLayout_LeftToRight, "Sangho")
   LNG(wxLANGUAGE_SANSKRIT,                   "sa"   , LANG_SANSKRIT  , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Sanskrit")
   LNG(wxLANGUAGE_SCOTS_GAELIC,               "gd"   , 0              , 0                                 , wxLayout_LeftToRight, "Scots Gaelic")
   LNG(wxLANGUAGE_SERBIAN,                    "sr_RS", LANG_SERBIAN   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Serbian")
   LNG(wxLANGUAGE_SERBIAN_CYRILLIC,           "sr_RS", LANG_SERBIAN   , SUBLANG_SERBIAN_CYRILLIC          , wxLayout_LeftToRight, "Serbian (Cyrillic)")
   LNG(wxLANGUAGE_SERBIAN_LATIN,              "sr_RS@latin", LANG_SERBIAN   , SUBLANG_SERBIAN_LATIN             , wxLayout_LeftToRight, "Serbian (Latin)")
   LNG(wxLANGUAGE_SERBIAN_CYRILLIC,           "sr_YU", LANG_SERBIAN   , SUBLANG_SERBIAN_CYRILLIC          , wxLayout_LeftToRight, "Serbian (Cyrillic)")
   LNG(wxLANGUAGE_SERBIAN_LATIN,              "sr_YU@latin", LANG_SERBIAN   , SUBLANG_SERBIAN_LATIN             , wxLayout_LeftToRight, "Serbian (Latin)")
   LNG(wxLANGUAGE_SERBO_CROATIAN,             "sh"   , 0              , 0                                 , wxLayout_LeftToRight, "Serbo-Croatian")
   LNG(wxLANGUAGE_SESOTHO,                    "st"   , 0              , 0                                 , wxLayout_LeftToRight, "Sesotho")
   LNG(wxLANGUAGE_SETSWANA,                   "tn"   , 0              , 0                                 , wxLayout_LeftToRight, "Setswana")
   LNG(wxLANGUAGE_SHONA,                      "sn"   , 0              , 0                                 , wxLayout_LeftToRight, "Shona")
   LNG(wxLANGUAGE_SINDHI,                     "sd"   , LANG_SINDHI    , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Sindhi")
   LNG(wxLANGUAGE_SINHALESE,                  "si"   , 0              , 0                                 , wxLayout_LeftToRight, "Sinhalese")
   LNG(wxLANGUAGE_SISWATI,                    "ss"   , 0              , 0                                 , wxLayout_LeftToRight, "Siswati")
   LNG(wxLANGUAGE_SLOVAK,                     "sk_SK", LANG_SLOVAK    , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Slovak")
   LNG(wxLANGUAGE_SLOVENIAN,                  "sl_SI", LANG_SLOVENIAN , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Slovenian")
   LNG(wxLANGUAGE_SOMALI,                     "so"   , 0              , 0                                 , wxLayout_LeftToRight, "Somali")
   LNG(wxLANGUAGE_SPANISH,                    "es_ES", LANG_SPANISH   , SUBLANG_SPANISH                   , wxLayout_LeftToRight, "Spanish")
   LNG(wxLANGUAGE_SPANISH_ARGENTINA,          "es_AR", LANG_SPANISH   , SUBLANG_SPANISH_ARGENTINA         , wxLayout_LeftToRight, "Spanish (Argentina)")
   LNG(wxLANGUAGE_SPANISH_BOLIVIA,            "es_BO", LANG_SPANISH   , SUBLANG_SPANISH_BOLIVIA           , wxLayout_LeftToRight, "Spanish (Bolivia)")
   LNG(wxLANGUAGE_SPANISH_CHILE,              "es_CL", LANG_SPANISH   , SUBLANG_SPANISH_CHILE             , wxLayout_LeftToRight, "Spanish (Chile)")
   LNG(wxLANGUAGE_SPANISH_COLOMBIA,           "es_CO", LANG_SPANISH   , SUBLANG_SPANISH_COLOMBIA          , wxLayout_LeftToRight, "Spanish (Colombia)")
   LNG(wxLANGUAGE_SPANISH_COSTA_RICA,         "es_CR", LANG_SPANISH   , SUBLANG_SPANISH_COSTA_RICA        , wxLayout_LeftToRight, "Spanish (Costa Rica)")
   LNG(wxLANGUAGE_SPANISH_DOMINICAN_REPUBLIC, "es_DO", LANG_SPANISH   , SUBLANG_SPANISH_DOMINICAN_REPUBLIC, wxLayout_LeftToRight, "Spanish (Dominican republic)")
   LNG(wxLANGUAGE_SPANISH_ECUADOR,            "es_EC", LANG_SPANISH   , SUBLANG_SPANISH_ECUADOR           , wxLayout_LeftToRight, "Spanish (Ecuador)")
   LNG(wxLANGUAGE_SPANISH_EL_SALVADOR,        "es_SV", LANG_SPANISH   , SUBLANG_SPANISH_EL_SALVADOR       , wxLayout_LeftToRight, "Spanish (El Salvador)")
   LNG(wxLANGUAGE_SPANISH_GUATEMALA,          "es_GT", LANG_SPANISH   , SUBLANG_SPANISH_GUATEMALA         , wxLayout_LeftToRight, "Spanish (Guatemala)")
   LNG(wxLANGUAGE_SPANISH_HONDURAS,           "es_HN", LANG_SPANISH   , SUBLANG_SPANISH_HONDURAS          , wxLayout_LeftToRight, "Spanish (Honduras)")
   LNG(wxLANGUAGE_SPANISH_MEXICAN,            "es_MX", LANG_SPANISH   , SUBLANG_SPANISH_MEXICAN           , wxLayout_LeftToRight, "Spanish (Mexican)")
   LNG(wxLANGUAGE_SPANISH_MODERN,             "es_ES", LANG_SPANISH   , SUBLANG_SPANISH_MODERN            , wxLayout_LeftToRight, "Spanish (Modern)")
   LNG(wxLANGUAGE_SPANISH_NICARAGUA,          "es_NI", LANG_SPANISH   , SUBLANG_SPANISH_NICARAGUA         , wxLayout_LeftToRight, "Spanish (Nicaragua)")
   LNG(wxLANGUAGE_SPANISH_PANAMA,             "es_PA", LANG_SPANISH   , SUBLANG_SPANISH_PANAMA            , wxLayout_LeftToRight, "Spanish (Panama)")
   LNG(wxLANGUAGE_SPANISH_PARAGUAY,           "es_PY", LANG_SPANISH   , SUBLANG_SPANISH_PARAGUAY          , wxLayout_LeftToRight, "Spanish (Paraguay)")
   LNG(wxLANGUAGE_SPANISH_PERU,               "es_PE", LANG_SPANISH   , SUBLANG_SPANISH_PERU              , wxLayout_LeftToRight, "Spanish (Peru)")
   LNG(wxLANGUAGE_SPANISH_PUERTO_RICO,        "es_PR", LANG_SPANISH   , SUBLANG_SPANISH_PUERTO_RICO       , wxLayout_LeftToRight, "Spanish (Puerto Rico)")
   LNG(wxLANGUAGE_SPANISH_URUGUAY,            "es_UY", LANG_SPANISH   , SUBLANG_SPANISH_URUGUAY           , wxLayout_LeftToRight, "Spanish (Uruguay)")
   LNG(wxLANGUAGE_SPANISH_US,                 "es_US", 0              , 0                                 , wxLayout_LeftToRight, "Spanish (U.S.)")
   LNG(wxLANGUAGE_SPANISH_VENEZUELA,          "es_VE", LANG_SPANISH   , SUBLANG_SPANISH_VENEZUELA         , wxLayout_LeftToRight, "Spanish (Venezuela)")
   LNG(wxLANGUAGE_SUNDANESE,                  "su"   , 0              , 0                                 , wxLayout_LeftToRight, "Sundanese")
   LNG(wxLANGUAGE_SWAHILI,                    "sw_KE", LANG_SWAHILI   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Swahili")
   LNG(wxLANGUAGE_SWEDISH,                    "sv_SE", LANG_SWEDISH   , SUBLANG_SWEDISH                   , wxLayout_LeftToRight, "Swedish")
   LNG(wxLANGUAGE_SWEDISH_FINLAND,            "sv_FI", LANG_SWEDISH   , SUBLANG_SWEDISH_FINLAND           , wxLayout_LeftToRight, "Swedish (Finland)")
   LNG(wxLANGUAGE_TAGALOG,                    "tl_PH", 0              , 0                                 , wxLayout_LeftToRight, "Tagalog")
   LNG(wxLANGUAGE_TAJIK,                      "tg"   , 0              , 0                                 , wxLayout_LeftToRight, "Tajik")
   LNG(wxLANGUAGE_TAMIL,                      "ta"   , LANG_TAMIL     , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Tamil")
   LNG(wxLANGUAGE_TATAR,                      "tt"   , LANG_TATAR     , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Tatar")
   LNG(wxLANGUAGE_TELUGU,                     "te"   , LANG_TELUGU    , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Telugu")
   LNG(wxLANGUAGE_THAI,                       "th_TH", LANG_THAI      , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Thai")
   LNG(wxLANGUAGE_TIBETAN,                    "bo"   , 0              , 0                                 , wxLayout_LeftToRight, "Tibetan")
   LNG(wxLANGUAGE_TIGRINYA,                   "ti"   , 0              , 0                                 , wxLayout_LeftToRight, "Tigrinya")
   LNG(wxLANGUAGE_TONGA,                      "to"   , 0              , 0                                 , wxLayout_LeftToRight, "Tonga")
   LNG(wxLANGUAGE_TSONGA,                     "ts"   , 0              , 0                                 , wxLayout_LeftToRight, "Tsonga")
   LNG(wxLANGUAGE_TURKISH,                    "tr_TR", LANG_TURKISH   , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Turkish")
   LNG(wxLANGUAGE_TURKMEN,                    "tk"   , 0              , 0                                 , wxLayout_LeftToRight, "Turkmen")
   LNG(wxLANGUAGE_TWI,                        "tw"   , 0              , 0                                 , wxLayout_LeftToRight, "Twi")
   LNG(wxLANGUAGE_UIGHUR,                     "ug"   , 0              , 0                                 , wxLayout_LeftToRight, "Uighur")
   LNG(wxLANGUAGE_UKRAINIAN,                  "uk_UA", LANG_UKRAINIAN , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Ukrainian")
   LNG(wxLANGUAGE_URDU,                       "ur"   , LANG_URDU      , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Urdu")
   LNG(wxLANGUAGE_URDU_INDIA,                 "ur_IN", LANG_URDU      , SUBLANG_URDU_INDIA                , wxLayout_LeftToRight, "Urdu (India)")
   LNG(wxLANGUAGE_URDU_PAKISTAN,              "ur_PK", LANG_URDU      , SUBLANG_URDU_PAKISTAN             , wxLayout_LeftToRight, "Urdu (Pakistan)")
   LNG(wxLANGUAGE_UZBEK,                      "uz"   , LANG_UZBEK     , SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Uzbek")
   LNG(wxLANGUAGE_UZBEK_CYRILLIC,             "uz"   , LANG_UZBEK     , SUBLANG_UZBEK_CYRILLIC            , wxLayout_LeftToRight, "Uzbek (Cyrillic)")
   LNG(wxLANGUAGE_UZBEK_LATIN,                "uz"   , LANG_UZBEK     , SUBLANG_UZBEK_LATIN               , wxLayout_LeftToRight, "Uzbek (Latin)")
   LNG(wxLANGUAGE_VALENCIAN,                  "ca_ES@valencia", 0              , 0                                 , wxLayout_LeftToRight, "Valencian (Southern Catalan)")
   LNG(wxLANGUAGE_VIETNAMESE,                 "vi_VN", LANG_VIETNAMESE, SUBLANG_DEFAULT                   , wxLayout_LeftToRight, "Vietnamese")
   LNG(wxLANGUAGE_VOLAPUK,                    "vo"   , 0              , 0                                 , wxLayout_LeftToRight, "Volapuk")
   LNG(wxLANGUAGE_WELSH,                      "cy"   , 0              , 0                                 , wxLayout_LeftToRight, "Welsh")
   LNG(wxLANGUAGE_WOLOF,                      "wo"   , 0              , 0                                 , wxLayout_LeftToRight, "Wolof")
   LNG(wxLANGUAGE_XHOSA,                      "xh"   , 0              , 0                                 , wxLayout_LeftToRight, "Xhosa")
   LNG(wxLANGUAGE_YIDDISH,                    "yi"   , 0              , 0                                 , wxLayout_LeftToRight, "Yiddish")
   LNG(wxLANGUAGE_YORUBA,                     "yo"   , 0              , 0                                 , wxLayout_LeftToRight, "Yoruba")
   LNG(wxLANGUAGE_ZHUANG,                     "za"   , 0              , 0                                 , wxLayout_LeftToRight, "Zhuang")
   LNG(wxLANGUAGE_ZULU,                       "zu"   , 0              , 0                                 , wxLayout_LeftToRight, "Zulu")

}
#undef LNG


#endif 