void setup()
{
  Serial.begin(9600);
}

void loop()
{
  Serial.println("Hello world");
  String input = "";
  boolean readIn = false;
  while (Serial.available() > 0)
  {
    readIn = true;
    char inChar = Serial.read();  
    if (inChar != '/')
    { 
      input.concat(inChar);
    }
    else
    {
      break;
    }
  }
  if (readIn)
  {
    Serial.println("Rec: " + input);
  }
  delay(1000);
}
