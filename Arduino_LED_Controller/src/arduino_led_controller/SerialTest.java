package arduino_led_controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.io.IOException;
import java.util.Enumeration;

// This code is taken from: http://playground.arduino.cc/Interfacing/Java#.UzhMQPldVAX

public class SerialTest implements SerialPortEventListener
{
    SerialPort serialPort;
    /** The port we're normally going to use. */
    private static final String PORT_NAMES[] = { 
                    "/dev/tty.usbserial-A9007UX1", // Mac OS X
                    "/dev/ttyUSB0", // Linux
                    "COM3", // Windows
    };
    
    /**
    * A BufferedReader which will be fed by a InputStreamReader 
    * converting the bytes into characters 
    * making the displayed results codepage independent
    */
    private BufferedReader input;
    /** The output stream to the port */
    private OutputStream output;
    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 9600;
    
    // Output the byte array to the serial port. Return whether or not this
    // succeeded
    public boolean output(byte[] b)
    {
        try
        {
            output.write(b);
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }

    // Return whether or not the connection was established
    public boolean initialize()
    {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements())
        {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName))
                {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null)
        {
            System.out.println("Could not find COM port.");
            return false;
        }
        else
        {
            System.out.println("Found the COM port.");
        }

        try
        {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                            TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            return true;
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
            return false;
        }
    }

    /**
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
    public synchronized void close()
    {
        if (serialPort != null)
        {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent)
    {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
                String inputLine = input.readLine();
                //System.out.println(inputLine);
                Arduino_LED_ControllerApp.controllerView.updateSerialOutputTextPane("REC: " + inputLine);
            }
            catch (Exception e)
            {
                //System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

    public static void main(String[] args) throws Exception
    {
        SerialTest main = new SerialTest();
        main.initialize();
        Thread t = new Thread()
        {
            public void run()
            {
                // the following line will keep this app alive for 1000 seconds,
                // waiting for events to occur and responding to them (printing incoming messages to console).
                try
                {
                    Thread.sleep(1000000);
                }
                catch (InterruptedException ie) {}
            }
        };
        t.start();
        System.out.println("Started");
    }
}