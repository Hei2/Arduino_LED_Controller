/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino_led_controller;

import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

// Useful for User32 SystemParametersInfo function: http://msdn.microsoft.com/en-us/library/ms724947%28v=vs.85%29.aspx

public interface User32 extends StdCallLibrary {
        boolean SystemParametersInfoA(int uiAction, int uiParam, Pointer pvParam, int fWinIni);
        public static final int SPI_GETDESKWALLPAPER = 0x0073;
}