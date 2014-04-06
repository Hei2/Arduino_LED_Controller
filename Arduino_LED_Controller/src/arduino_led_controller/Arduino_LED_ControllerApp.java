package arduino_led_controller;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class Arduino_LED_ControllerApp extends SingleFrameApplication
{
    public static Arduino_LED_ControllerView controllerView;
    
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup()
    {
        show(controllerView = new Arduino_LED_ControllerView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root)
    {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of Arduino_LED_ControllerApp
     */
    public static Arduino_LED_ControllerApp getApplication()
    {
        return Application.getInstance(Arduino_LED_ControllerApp.class);
    }
    
    public static SerialTest serialTest;

    /**
     * Main method launching the application.
     */
    public static void main (String[] args) throws Exception 
    {
        serialTest = new SerialTest();
        launch(Arduino_LED_ControllerApp.class, args);
    }
}
