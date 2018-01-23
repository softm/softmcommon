package net.softm.lib.common;    

/**
 * CallBackEvent
 * @author softm
 */
public interface CallBackEvent  {
    public void success(String eventId,Object dto);
    public void error(String eventId,Object dto);        
}	