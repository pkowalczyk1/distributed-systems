//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.9
//
// <auto-generated>
//
// Generated from file `home.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package pl.agh.edu.intelligenthome.generated.smarthome;

public class InvalidBrightnessValueException extends com.zeroc.Ice.UserException
{
    public InvalidBrightnessValueException()
    {
    }

    public InvalidBrightnessValueException(Throwable cause)
    {
        super(cause);
    }

    public String ice_id()
    {
        return "::smarthome::InvalidBrightnessValueException";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::smarthome::InvalidBrightnessValueException", -1, true);
        ostr_.endSlice();
    }

    /** @hidden */
    @Override
    protected void _readImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        istr_.endSlice();
    }

    /** @hidden */
    public static final long serialVersionUID = 812878241415678549L;
}
