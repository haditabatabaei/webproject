


#include "wx/wxprec.h"

#ifdef __BORLANDC__
    #pragma hdrstop
#endif

#if wxUSE_HEADERCTRL

#include "wx/headercol.h"


int wxHeaderColumn::GetFromIndividualFlags() const
{
    int flags = 0;

    if ( IsResizeable() )
        flags |= wxCOL_RESIZABLE;
    if ( IsSortable() )
        flags |= wxCOL_SORTABLE;
    if ( IsReorderable() )
        flags |= wxCOL_REORDERABLE;
    if ( IsHidden() )
        flags |= wxCOL_HIDDEN;

    return flags;
}


void wxSettableHeaderColumn::SetIndividualFlags(int flags)
{
    SetResizeable((flags & wxCOL_RESIZABLE) != 0);
    SetSortable((flags & wxCOL_SORTABLE) != 0);
    SetReorderable((flags & wxCOL_REORDERABLE) != 0);
    SetHidden((flags & wxCOL_HIDDEN) != 0);
}

void wxSettableHeaderColumn::ChangeFlag(int flag, bool set)
{
    if ( HasFlag(flag) != set )
        ToggleFlag(flag);
}

void wxSettableHeaderColumn::SetFlag(int flag)
{
    int flags = GetFlags();
    if ( !(flags & flag) )
        SetFlags(flags | flag);
}

void wxSettableHeaderColumn::ClearFlag(int flag)
{
    int flags = GetFlags();
    if ( flags & flag )
        SetFlags(flags & ~flag);
}

void wxSettableHeaderColumn::ToggleFlag(int flag)
{
    int flags = GetFlags();
    if ( flags & flag )
        flags &= ~flag;
    else
        flags |= flag;

    SetFlags(flags);
}

#endif 