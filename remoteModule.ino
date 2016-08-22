#include <SoftwareSerial.h>

int IRledPin = 13;
SoftwareSerial mySerial(2,3);//RX TX
char deg;
bool isWork = false;
bool asdf = false;
String a;
String b;
int toDeg = 25;
int nowDeg = 25;

//T = 84 F = 70

void setup() {
  pinMode(4, OUTPUT);

  pinMode(IRledPin, OUTPUT);
  
  Serial.begin(9600);
  Serial.println("Serial hello!");

  mySerial.begin(9600);
  mySerial.println("hello!");
}

void loop() {
  digitalWrite(4, HIGH);
  
   if(mySerial.available()){
//    Serial.write(mySerial.read());
    deg = mySerial.read();
//    Serial.println(deg);
    if(deg == 'T' || deg == 'F'){
      if(deg == 'T'){
        TurnOn();
        isWork = true;
        Serial.println("ON");
      }else{
        TurnOff();
        isWork = false;
        Serial.println("Off");
        nowDeg = 25;
      }
    }else{
      a = String(deg);
      b+=a;
//    Serial.println(a);
      if(asdf){
//      Serial.println(b.toInt());
        toDeg = b.toInt();
        b="";
        asdf = false;
      }else{
        asdf = true;
      }
    }
//    Serial.println(mySerial.read());
//    Serial.write(mySerial.read() == 1);
   }
   if(Serial.available()){
    mySerial.write(Serial.read());
   }
   if(isWork){
      if(nowDeg > toDeg){
    TempDown();
    nowDeg--;
    Serial.print("Now Degree : ");
    Serial.print(nowDeg);
    Serial.print("      To Degree : ");
    Serial.println(toDeg);
    delay(1000);
   }else if(nowDeg < toDeg){
    TempUp();
    nowDeg++;
    Serial.print("Now Degree : ");
    Serial.print(nowDeg);
    Serial.print("      To Degree : ");
    Serial.println(toDeg);
    delay(1000);
   }
   }
}

void pulseIR(long microsecs) {
  // we'll count down from the number of microseconds we are told to wait
 
  cli();  // this turns off any background interrupts
 
  while (microsecs > 0) {
    // 38 kHz is about 13 microseconds high and 13 microseconds low
   digitalWrite(IRledPin, HIGH);  // this takes about 3 microseconds to happen
   delayMicroseconds(10);         // hang out for 10 microseconds
   digitalWrite(IRledPin, LOW);   // this also takes about 3 microseconds
   delayMicroseconds(10);         // hang out for 10 microseconds
   
   // so 26 microseconds altogether
   microsecs -= 26;
  }
 
  sei();  // this turns them back on
}
void TurnOn() {
  delayMicroseconds(31748); //Time off (Left Column on serial monitor)
  pulseIR(2940);           //Time on  (Right Column on serial monitor)
  delayMicroseconds(8880);
  pulseIR(500);
  delayMicroseconds(1520);
  pulseIR(480);
  delayMicroseconds(540);
  pulseIR(500);
  delayMicroseconds(560);
  pulseIR(460);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(560);
  pulseIR(460);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(520);
  pulseIR(480);
  delayMicroseconds(540);
  pulseIR(520);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(1500);
  pulseIR(480);
  delayMicroseconds(560);
  pulseIR(480);
  delayMicroseconds(580);
  pulseIR(460);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(540);
  pulseIR(480);
  delayMicroseconds(1580);
  pulseIR(460);
  delayMicroseconds(1580);
  pulseIR(500);
  delayMicroseconds(540);
  pulseIR(520);
  delayMicroseconds(1500);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(500);
  delayMicroseconds(1520);
  pulseIR(500);
  delayMicroseconds(1500);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(520);
  delayMicroseconds(1540);
  pulseIR(460);
  delayMicroseconds(1580);
  pulseIR(480);
  delayMicroseconds(1600);
  pulseIR(480);
  delayMicroseconds(540);
  pulseIR(520);
  delayMicroseconds(540);
  pulseIR(460);
  delayMicroseconds(580);
  pulseIR(480);
  delayMicroseconds(540);
  pulseIR(520);
  delayMicroseconds(1480);
  pulseIR(500);
  delayMicroseconds(560);
  pulseIR(460);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(1500);
  pulseIR(500);
  delayMicroseconds(1520);
  pulseIR(480);
  delayMicroseconds(540);
  pulseIR(480);
  delayMicroseconds(580);
  pulseIR(520);
  delayMicroseconds(1500);
  pulseIR(520);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(520);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(480);
  delayMicroseconds(1580);
  pulseIR(460);
  delayMicroseconds(560);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(480);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(540);
  pulseIR(440);
  delayMicroseconds(560);
  pulseIR(460);
  delayMicroseconds(580);
  pulseIR(480);
  delayMicroseconds(540);
  pulseIR(520);
  delayMicroseconds(540);
  pulseIR(440);
  delayMicroseconds(560);
  pulseIR(480);
  delayMicroseconds(600);
  pulseIR(500);
  delayMicroseconds(1500);
  pulseIR(460);
  delayMicroseconds(1560);
  pulseIR(460);
  delayMicroseconds(1560);
  pulseIR(440);
  delayMicroseconds(1560);
  pulseIR(640);
}

void TempUp() {
  // This is the code for the CHANNEL + for the downstairs TV COMCAST
  delayMicroseconds(13712); //Time off (Left Column on serial monitor)
  pulseIR(2920);           //Time on  (Right Column on serial monitor)
  delayMicroseconds(8880);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(480);
  delayMicroseconds(540);
  pulseIR(500);
  delayMicroseconds(520);
  pulseIR(500);
  delayMicroseconds(520);
  pulseIR(500);
  delayMicroseconds(560);
  pulseIR(460);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(540);
  pulseIR(480);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(540);
  pulseIR(480);
  delayMicroseconds(1520);
  pulseIR(500);
  delayMicroseconds(520);
  pulseIR(500);
  delayMicroseconds(520);
  pulseIR(500);
  delayMicroseconds(520);
  pulseIR(500);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(1500);
  pulseIR(540);
  delayMicroseconds(1540);
  pulseIR(540);
  delayMicroseconds(500);
  pulseIR(540);
  delayMicroseconds(1480);
  pulseIR(500);
  delayMicroseconds(1540);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(500);
  delayMicroseconds(1500);
  pulseIR(520);
  delayMicroseconds(1560);
  pulseIR(460);
  delayMicroseconds(1540);
  pulseIR(460);
  delayMicroseconds(1560);
  pulseIR(500);
  delayMicroseconds(1520);
  pulseIR(500);
  delayMicroseconds(520);
  pulseIR(560);
  delayMicroseconds(520);
  pulseIR(500);
  delayMicroseconds(520);
  pulseIR(480);
  delayMicroseconds(560);
  pulseIR(460);
  delayMicroseconds(1560);
  pulseIR(480);
  delayMicroseconds(540);
  pulseIR(460);
  delayMicroseconds(580);
  pulseIR(500);
  delayMicroseconds(1500);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(520);
  delayMicroseconds(540);
  pulseIR(460);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(540);
  pulseIR(480);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(520);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(460);
  delayMicroseconds(1540);
  pulseIR(460);
  delayMicroseconds(600);
  pulseIR(480);
  delayMicroseconds(1600);
  pulseIR(520);
  delayMicroseconds(1480);
  pulseIR(480);
  delayMicroseconds(580);
  pulseIR(460);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(540);
  pulseIR(500);
  delayMicroseconds(500);
  pulseIR(500);
  delayMicroseconds(540);
  pulseIR(480);
  delayMicroseconds(560);
  pulseIR(480);
  delayMicroseconds(560);
  pulseIR(480);
  delayMicroseconds(540);
  pulseIR(500);
  delayMicroseconds(1500);
  pulseIR(480);
  delayMicroseconds(1560);
  pulseIR(460);
  delayMicroseconds(1560);
  pulseIR(500);
  delayMicroseconds(1520);
  pulseIR(440);
}

void TempDown() {
  // This is the code for the CHANNEL + for the downstairs TV COMCAST
  delayMicroseconds(56600); //Time off (Left Column on serial monitor)
  pulseIR(2940);           //Time on  (Right Column on serial monitor)
  delayMicroseconds(8900);
  pulseIR(480);
  delayMicroseconds(1560);
  pulseIR(500);
  delayMicroseconds(520);
  pulseIR(460);
  delayMicroseconds(580);
  pulseIR(460);
  delayMicroseconds(560);
  pulseIR(480);
  delayMicroseconds(540);
  pulseIR(500);
  delayMicroseconds(560);
  pulseIR(480);
  delayMicroseconds(560);
  pulseIR(480);
  delayMicroseconds(540);
  pulseIR(500);
  delayMicroseconds(560);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(520);
  delayMicroseconds(580);
  pulseIR(480);
  delayMicroseconds(540);
  pulseIR(480);
  delayMicroseconds(580);
  pulseIR(480);
  delayMicroseconds(560);
  pulseIR(480);
  delayMicroseconds(1580);
  pulseIR(480);
  delayMicroseconds(1580);
  pulseIR(460);
  delayMicroseconds(580);
  pulseIR(480);
  delayMicroseconds(1520);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(520);
  delayMicroseconds(1480);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(480);
  delayMicroseconds(1560);
  pulseIR(460);
  delayMicroseconds(1580);
  pulseIR(480);
  delayMicroseconds(1580);
  pulseIR(500);
  delayMicroseconds(1540);
  pulseIR(480);
  delayMicroseconds(580);
  pulseIR(460);
  delayMicroseconds(580);
  pulseIR(480);
  delayMicroseconds(580);
  pulseIR(440);
  delayMicroseconds(580);
  pulseIR(460);
  delayMicroseconds(1540);
  pulseIR(460);
  delayMicroseconds(580);
  pulseIR(440);
  delayMicroseconds(580);
  pulseIR(500);
  delayMicroseconds(1500);
  pulseIR(480);
  delayMicroseconds(1560);
  pulseIR(500);
  delayMicroseconds(520);
  pulseIR(460);
  delayMicroseconds(560);
  pulseIR(560);
  delayMicroseconds(1500);
  pulseIR(520);
  delayMicroseconds(540);
  pulseIR(480);
  delayMicroseconds(580);
  pulseIR(440);
  delayMicroseconds(1560);
  pulseIR(460);
  delayMicroseconds(1580);
  pulseIR(520);
  delayMicroseconds(500);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(520);
  delayMicroseconds(1500);
  pulseIR(460);
  delayMicroseconds(580);
  pulseIR(500);
  delayMicroseconds(540);
  pulseIR(480);
  delayMicroseconds(560);
  pulseIR(440);
  delayMicroseconds(540);
  pulseIR(480);
  delayMicroseconds(580);
  pulseIR(460);
  delayMicroseconds(560);
  pulseIR(500);
  delayMicroseconds(560);
  pulseIR(460);
  delayMicroseconds(580);
  pulseIR(440);
  delayMicroseconds(1560);
  pulseIR(480);
  delayMicroseconds(1540);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(440);
  delayMicroseconds(1540);
  pulseIR(580);
}

void TurnOff() {
  delayMicroseconds(23516); //Time off (Left Column on serial monitor)
  pulseIR(3100);           //Time on  (Right Column on serial monitor)
  delayMicroseconds(8620);
  pulseIR(520);
  delayMicroseconds(1540);
  pulseIR(500);
  delayMicroseconds(500);
  pulseIR(500);
  delayMicroseconds(500);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(500);
  pulseIR(500);
  delayMicroseconds(500);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(500);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(500);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(500);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(500);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(520);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(520);
  pulseIR(520);
  delayMicroseconds(1520);
  pulseIR(500);
  delayMicroseconds(500);
  pulseIR(500);
  delayMicroseconds(500);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(500);
  pulseIR(500);
  delayMicroseconds(500);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(480);
  pulseIR(520);
  delayMicroseconds(520);
  pulseIR(500);
  delayMicroseconds(1540);
  pulseIR(500);
  delayMicroseconds(1540);
  pulseIR(500);
}

