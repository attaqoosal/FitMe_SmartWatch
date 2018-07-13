
// I2Cdev and MPU9250 must be installed as libraries, or else the .cpp/.h files
// for both classes must be in the include path of your project
#include "I2Cdev.h"

//SPI Transfer to Another One
#include <SPI.h>

//for abs Value
#include <math.h>

#include "MPU9250_9Axis_MotionApps41.h"

//Bluetooth RX,TX
#include <SoftwareSerial.h>

// Arduino Wire library is required if I2Cdev I2CDEV_ARDUINO_WIRE implementation
// is used in I2Cdev.h
#if I2CDEV_IMPLEMENTATION == I2CDEV_ARDUINO_WIRE
#include "Wire.h"
#endif

// class default I2C address is 0x68
// specific I2C addresses may be passed as a parameter here
// AD0 low = 0x68 (default for SparkFun breakout and InvenSense evaluation board)
// AD0 high = 0x69
MPU9250 mpu; // <-- use for AD0 high

// uncomment "OUTPUT_READABLE_QUATERNION" if you want to see the actual
// quaternion components in a [w, x, y, z] format (not best for parsing
// on a remote host such as Processing or something though)
// #define OUTPUT_READABLE_QUATERNION

//Bluetooth RX,TX
SoftwareSerial hm10(3, 4);

//PIN SETTINGS about intterupt and buttons
#define INTERRUPT_PIN 2  // use pin 2 on Arduino Uno & most boards
#define BUTTON1 5        // use pin 5~8 buttons
#define BUTTON2 6
#define BUTTON3 7
#define BUTTON4 8

//  Pulse pins Variables
int pulsePin = 0;                 // Pulse Sensor purple wire connected to analog pin 0
int blinkPin = 1;                // pin to blink led at each beat
int fadePin = 9;                  // pin to do fancy classy fading blink at each beat
int fadeRate = 0;                 // used to fade LED on with PWM on fadePin

// MPU control/status vars
volatile boolean mpuInterrupt = false;     // indicates whether MPU interrupt pin has gone high
volatile boolean dmpReady = false;  // set true if DMP init was successful
volatile uint8_t mpuIntStatus;   // holds actual interrupt status byte from MPU
volatile uint8_t devStatus;      // return status after each device operation (0 = success, !0 = error)
volatile uint16_t packetSize;    // expected DMP packet size (default is 42 bytes)
volatile uint16_t fifoCount;     // count of all bytes currently in FIFO
volatile uint8_t fifoBuffer[64]; // FIFO storage buffer
volatile boolean mpuFifoCheck;   //mpuintturupt section check

//Time Check
uint32_t pass_prev_time;
uint32_t run_prev_time;
uint32_t run_pass_prev_time;
uint32_t run_dist_prev_time;
uint32_t dumb_prev_time;
uint32_t barb_prev_time;
uint32_t rope_prev_time;
uint32_t situp_prev_time;
uint32_t sleep_prev_time;

// == final calculate save data and transfer parameter == //

//menuSelect
uint8_t buttonDown1;
uint8_t buttonDown2;
uint8_t buttonDown3;
uint8_t buttonDown4;
uint8_t menuSelect;
int intturuptCheck[4] = {0, 0, 0, 0};
uint8_t beefs;
boolean mutes;
boolean freezeMenu;
boolean buttonDown2Check;
boolean buttonDown3Check;

//test pass bluetooth data
uint32_t prev_BLE_time;
uint32_t current_BLE_time;
uint32_t BLECount;
uint32_t BLECount_M;
uint32_t BLECount_C;
uint32_t BLECount_K;
uint32_t BLE_first_prev_time;
uint32_t BLE_second_prev_time;
boolean BLE_Auto_Tran_Checker;

//for SPISaveData
int8_t ModifyData[8];
byte SPIData[8];

// count parameters
uint32_t psmeter;
uint32_t psmeterTotal;
uint32_t distmeter;
uint32_t distmeterTotal;
uint32_t run_psmeter;
uint32_t run_psmeterTotal;
uint32_t run_distmeter;
uint32_t run_distmeterTotal;
uint32_t dumbmeter;
uint32_t dumbmeterTotal;
uint32_t barbmeter;
uint32_t barbmeterTotal;
uint32_t ropemeter;
uint32_t ropemeterTotal;
uint32_t situpmeter;
uint32_t situpmeterTotal;
uint32_t hbmeter;
uint16_t hbmeterTotal[6];
uint8_t shallowSleepmeter;
uint8_t sleepFailCount;

//for timeChange parameters
uint8_t TCButtonDown1;
uint8_t TCButtonDown2;
uint8_t TCButtonDown3;
uint8_t TCButtonDown4;
uint8_t weekChange;
uint8_t yearChange;
uint8_t monthChange;
uint8_t dayChange;
uint8_t hourChange;
uint8_t minutsChange;
uint8_t TChangeWhere;
uint8_t TChangeOK;

//for passometer(shock) parameters
int16_t passAccel[3];       // [x, y, z]   yaw/pitch/roll container and gravity vector
float psFstX;
float psFstY;
float psFstZ;
boolean psRoofCount;
boolean psResult;

//for passometer(distance) parameters
int16_t distAccel[3];       // [x, y, z]   yaw/pitch/roll container and gravity vector
boolean distStartCheck;
int16_t distPrevX;
int16_t distPrevY;
int16_t distPrevZ;
uint8_t distCount;

//for run(shock) parameters
int16_t run_passAccel[3];       // [x, y, z]   yaw/pitch/roll container and gravity vector
float run_psFstX;
float run_psFstY;
float run_psFstZ;
boolean run_psRoofCount;
boolean run_psResult;

//for run(distance) parameters
int16_t run_distAccel[3];       // [x, y, z]   yaw/pitch/roll container and gravity vector
boolean run_distStartCheck;
int16_t run_distPrevX;
int16_t run_distPrevY;
int16_t run_distPrevZ;
uint8_t run_distCount;

//for dumbbell parameters
int16_t dumbAccel[3];       // [x, y, z]   yaw/pitch/roll container and gravity vector
int16_t dumbFstZ;
int16_t dumbPrevZ;
boolean dumbStartCheck;
boolean dumbUpCheck;
uint16_t dumbRunCount;

//for barbell parameters
int16_t barbAccel[3];       // [x, y, z]   yaw/pitch/roll container and gravity vector
int16_t barbFstZ;
int16_t barbPrevZ;
boolean barbStartCheck;
boolean barbUpCheck;
uint16_t barbRunCount;

//for jumprope parameters
int16_t ropeAccel[3];       // [x, y, z]   yaw/pitch/roll container and gravity vector
int16_t ropeFstZ;
int16_t ropePrevZ;
boolean ropeStartCheck;
boolean ropeUpCheck;
uint16_t ropeRunCount;

//for situp parameters
int16_t situpAccel[3];       // [x, y, z]   yaw/pitch/roll container and gravity vector
int16_t situpFstZ;
int16_t situpPrevZ;
boolean situpStartCheck;
boolean situpUpCheck;
uint16_t situpRunCount;

// Pulse Volatile Variables, used in the interrupt service routine!
volatile int BPM;                   // int that holds raw Analog in 0. updated every 2mS
volatile int Signal;                // holds the incoming raw data
volatile int IBI = 600;             // int that holds the time interval between beats! Must be seeded!
volatile boolean Pulse = false;     // "True" when User's live heartbeat is detected. "False" when not a "live beat".
volatile boolean QS = false;        // becomes true when Arduoino finds a beat.

//HeartBeat intturupt values
volatile int rate[10];                    // array to hold last ten IBI values
volatile unsigned long sampleCounter = 0;          // used to determine pulse timing
volatile unsigned long lastBeatTime = 0;           // used to find IBI
volatile int P = 512;                     // used to find peak in pulse wave, seeded
volatile int T = 512;                     // used to find trough in pulse wave, seeded
volatile int thresh = 530;                // used to find instant moment of heart beat, seeded
volatile int amp = 0;                   // used to hold amplitude of pulse waveform, seeded
volatile boolean firstBeat = true;        // used to seed rate array so we startup with reasonable BPM
volatile boolean secondBeat = false;      // used to seed rate array so we startup with reasonable BPM

//for take a break;
uint32_t prev_heartbeat_time;
uint32_t prev_HBTimeCheck;

//for final pulse calculate data
uint32_t TotalBPM;
uint32_t hbmeterAcc;
uint16_t hbmeterCount;
uint8_t hbSequence;

//for sleep parameters
int16_t sleepAccel[3];       // [x, y, z]   yaw/pitch/roll container and gravity vector
float sleepPrevX;
float sleepPrevY;
float sleepPrevZ;
int8_t deepSleepCount;
int8_t shallowSleepCount;
int8_t sleepTFailCount;
uint32_t sleepTimeCount;

// ================================================================
// ===                      INITIAL SETUP                       ===
// ================================================================

void setup() {
  // join I2C bus (I2Cdev library doesn't do this automatically)
#if I2CDEV_IMPLEMENTATION == I2CDEV_ARDUINO_WIRE
  Wire.begin();
  Wire.setClock(400000); // 400kHz I2C clock. Comment this line if having compilation difficulties
#elif I2CDEV_IMPLEMENTATION == I2CDEV_BUILTIN_FASTWIRE
  Fastwire::setup(400, true);
#endif

  //Button Pin mode
  pinMode(BUTTON1, INPUT_PULLUP);
  pinMode(BUTTON2, INPUT_PULLUP);
  pinMode(BUTTON3, INPUT_PULLUP);
  pinMode(BUTTON4, INPUT_PULLUP);

  //HB PIN MODE
  pinMode(blinkPin, OUTPUT);        // pin that will blink to your heartbeat!
  pinMode(fadePin, OUTPUT);         // pin that will fade to your heartbeat!

  //SPI transfer
  pinMode(MISO, OUTPUT);
  pinMode(MOSI, INPUT);
  pinMode(SCK, INPUT);
  pinMode(9, INPUT);
  SPI.setClockDivider(SPI_CLOCK_DIV16);

  //SPI Register Settings for
  SPCR |= _BV(SPE); // SPI On
  SPCR &= ~_BV(MSTR); // Choose Slave Mode
  SPCR |= _BV(SPIE); // Intterupt recognize

  // initialize serial communication
  Serial.begin(115200);
  hm10.begin(9600);
  while (!Serial); // wait for Leonardo enumeration, others continue immediately

  // NOTE: 8MHz or slower host processors, like the Teensy @ 3.3v or Ardunio
  // Pro Mini running at 3.3v, cannot handle this baud rate reliably due to
  // the baud timing being too misaligned with processor ticks. You must use
  // 38400 or slower in these cases, or use some kind of external separate
  // crystal solution for the UART timer.

  // initialize device
  mpu.initialize();
  heartBeatInterruptSetup();         // sets up to read Pulse Sensor signal every 2mS
  pinMode(INTERRUPT_PIN, INPUT);    //must setup after 9250 initialize

  // verify connection
  mpu.testConnection();

  // wait for ready
  //  while (Serial.available() && Serial.read()); // empty buffer
  //  while (!Serial.available());                 // wait for data
  //  while (Serial.available() && Serial.read()); // empty buffer again

  // load and configure the DMP
  devStatus = mpu.dmpInitialize();

  // make sure it worked (returns 0 if so)
  if (devStatus == 0) {
    // turn on the DMP, now that it's ready
    mpu.setDMPEnabled(true);

    // enable Arduino interrupt detection
    attachInterrupt(digitalPinToInterrupt(INTERRUPT_PIN), dmpDataReady, RISING);
    mpuIntStatus = mpu.getIntStatus();

    // set our DMP Ready flag so the main loop() function knows it's okay to use it
    dmpReady = true;

    // get expected DMP packet size for later comparison
    packetSize = mpu.dmpGetFIFOPacketSize();
  } else {
    // ERROR!
    // 1 = initial memory load failed
    // 2 = DMP configuration updates failed
    // (if it's going to break, usually the code will be 1)
    Serial.print(devStatus);
  }
}

// ================================================================
// ===                 ABOUT VECT INTTERRUPTS                   ===
// ================================================================

//SPI intterupt Routine(SPI Transfer)
ISR(SPI_STC_vect) {
  byte command = SPDR; // Gain SPIData..
  //Always transfer datas
  if (command == 0) {
    SPDR = SPIData[1];
  }
  if (command == 1) {
    SPDR = SPIData[2];
  }
  if (command == 2) {
    SPDR = SPIData[3];
  }
  if (command == 3) {
    SPDR = SPIData[4];
  }
  if (command == 4) {
    SPDR = SPIData[5];
  }
  if (command == 5) {
    SPDR = SPIData[6];
  }
  if (command == 6) {
    SPDR = SPIData[7];
  }
  //music beef
  if (command == 7) {
    SPDR = beefs;
  }
  //menu
  if (command == 16) {
    SPDR = SPIData[0];
  }
  //bluetooth transfer when battery down...
  if (command == 8) {
    BLEAllTransfer();
    initializer();
    SPDR = 1;
  }
  //bluetooth transfer auto data
  if (command == 9) {
    BLE_Auto_Tran_Checker = true;
    BLE_first_prev_time = millis();
    BLE_second_prev_time = millis();
    SPDR = 1;
  }
  if (command == 10) {
    BLEAllTransfer();
    initializer();
    SPDR = 1;
  }
  //Time initialize
  if (command == 11) {
    weekChange = 0;
    SPDR = 1;
  }
  if (command == 12) {
    monthChange = 0;
    SPDR = 1;
  }
  if (command == 13) {
    dayChange = 0;
    SPDR = 1;
  }
  if (command == 14) {
    hourChange = 0;
    SPDR = 1;
  }
  if (command == 15) {
    minutsChange = 0;
    SPDR = 1;
  }

  //if saved changed, go to menu
  //initialize TimeChange increase / decrease data
  if (command == 6 && SPIData[7] == 9) {
    initTimeChange();
    TChangeWhere = 1;
    TChangeOK = 0;
    freezeMenu = false;
  }
}

// ================================================================
// ===              ABOUT HEARTBEAT INTTERRUPTS                 ===
// ================================================================

// THIS IS THE TIMER 2 INTERRUPT SERVICE ROUTINE.
// Timer 2 makes sure that we take a reading every 2 miliseconds
ISR(TIMER2_COMPA_vect) {                        // triggered when Timer2 counts to 124
  cli();                                      // disable interrupts while we do this
  Signal = analogRead(pulsePin);              // read the Pulse Sensor
  sampleCounter += 2;                         // keep track of the time in mS with this variable
  int N = sampleCounter - lastBeatTime;       // monitor the time since the last beat to avoid noise

  //  find the peak and trough of the pulse wave
  if (Signal < thresh && N > (IBI / 5) * 3) { // avoid dichrotic noise by waiting 3/5 of last IBI
    if (Signal < T) {                       // T is the trough
      T = Signal;                         // keep track of lowest point in pulse wave
    }
  }

  if (Signal > thresh && Signal > P) {        // thresh condition helps avoid noise
    P = Signal;                             // P is the peak
  }                                        // keep track of highest point in pulse wave

  //  NOW IT'S TIME TO LOOK FOR THE HEART BEAT
  // signal surges up in value every time there is a pulse
  if (N > 250) {                                  // avoid high frequency noise
    if ( (Signal > thresh) && (Pulse == false) && (N > (IBI / 5) * 3) ) {
      Pulse = true;                               // set the Pulse flag when we think there is a pulse
      digitalWrite(blinkPin, HIGH);               // turn on pin 13 LED
      IBI = sampleCounter - lastBeatTime;         // measure time between beats in mS
      lastBeatTime = sampleCounter;               // keep track of time for next pulse

      if (secondBeat) {                      // if this is the second beat, if secondBeat == TRUE
        secondBeat = false;                  // clear secondBeat flag
        for (int i = 0; i <= 9; i++) {       // seed the running total to get a realisitic BPM at startup
          rate[i] = IBI;
        }
      }

      if (firstBeat) {                       // if it's the first time we found a beat, if firstBeat == TRUE
        firstBeat = false;                   // clear firstBeat flag
        secondBeat = true;                   // set the second beat flag
        sei();                               // enable interrupts again
        return;                              // IBI value is unreliable so discard it
      }


      // keep a running total of the last 10 IBI values
      word runningTotal = 0;                  // clear the runningTotal variable

      for (int i = 0; i <= 8; i++) {          // shift data in the rate array
        rate[i] = rate[i + 1];                // and drop the oldest IBI value
        runningTotal += rate[i];              // add up the 9 oldest IBI values
      }

      rate[9] = IBI;                          // add the latest IBI to the rate array
      runningTotal += rate[9];                // add the latest IBI to runningTotal
      runningTotal /= 10;                     // average the last 10 IBI values
      BPM = 60000 / runningTotal;             // how many beats can fit into a minute? that's BPM!
      QS = true;                              // set Quantified Self flag
      // QS FLAG IS NOT CLEARED INSIDE THIS ISR
    }
  }

  if (Signal < thresh && Pulse == true) {  // when the values are going down, the beat is over
    digitalWrite(blinkPin, LOW);           // turn off pin 13 LED
    Pulse = false;                         // reset the Pulse flag so we can do it again
    amp = P - T;                           // get amplitude of the pulse wave
    thresh = amp / 2 + T;                  // set thresh at 50% of the amplitude
    P = thresh;                            // reset these for next time
    T = thresh;
  }

  if (N > 2500) {                          // if 2.5 seconds go by without a beat
    thresh = 530;                          // set thresh default
    P = 512;                               // set P default
    T = 512;                               // set T default
    lastBeatTime = sampleCounter;          // bring the lastBeatTime up to date
    firstBeat = true;                      // set these to avoid noise
    secondBeat = false;                    // when we get the heartbeat back
  }
  BPM < 200 ? TotalBPM += BPM : TotalBPM;  //count TotalBPM
  sei();                                   // enable interrupts when youre done!
}// end isr

// ================================================================
// ===            MPU INTERRUPT DETECTION ROUTINE               ===
// ================================================================

void dmpDataReady() {
  mpuInterrupt = true;
}

// ================================================================
// ===            MPU INTERRUPT DETECTION ROUTINE               ===
// ================================================================

// CHECK OUT THE Timer_Interrupt_Notes TAB FOR MORE ON INTERRUPTS
void heartBeatInterruptSetup() {
  // Initializes Timer2 to throw an interrupt every 2mS.
  TCCR2A = 0x02;     // DISABLE PWM ON DIGITAL PINS 3 AND 11, AND GO INTO CTC MODE
  TCCR2B = 0x06;     // DON'T FORCE COMPARE, 256 PRESCALER
  OCR2A = 0X7C;      // SET THE TOP OF THE COUNT TO 124 FOR 500Hz SAMPLE RATE
  TIMSK2 = 0x02;     // ENABLE INTERRUPT ON MATCH BETWEEN TIMER2 AND OCR2A
  sei();             // MAKE SURE GLOBAL INTERRUPTS ARE ENABLED
}


// ================================================================
// ===                            LOOP                          ===
// ================================================================

//algorithm
//1. menucheck
//2. action by menucheck
//3. transfer action(in bluetooth and etc...)

void loop() {
  uint32_t current_time = millis();

  if ( BLE_Auto_Tran_Checker == true ) {
    if (current_time - BLE_first_prev_time > 1000) {
      BLEPassTransfer();
      BLE_first_prev_time = current_time;
    }
    if (current_time - BLE_second_prev_time > 2000) {
      BLEHBTransfer();
      BLE_second_prev_time = current_time;
      BLE_Auto_Tran_Checker = false;
    }
  }

  //MPU_9250 catch fifoBuffer Action for Main Programs...
  MPU_fifoBuffer();

  //main menu loop...
  freezeMenu == false ? menu() : timeChange(); //if parameter freezeMenu false, run menu.
  if (mpuFifoCheck == true) {
    if (menuSelect != 8 && menuSelect != 2) {
      passometer();
      distance();
    }
    if (menuSelect == 2) {
      run_passometer();
      run_distance();
    }
    else if (menuSelect == 3) {
      dumbbell();
    }
    else if (menuSelect == 4) {
      barbell();
    }
    else if (menuSelect == 5) {
      jumprope();
    }
    else if (menuSelect == 6) {
      heartBeat();
    }
    else if (menuSelect == 7) {
      situp();
    }
    else if (menuSelect == 8) {
      sleep();
      if (current_time - sleepTimeCount > 3600000) {
        BLESleepTransfer();
        sleepTimeCount = current_time;
      }
    }
    else {
      sleepTimeCount = current_time;
      shallowSleepmeter = 0;
    }
  }
  //SPIDataSettings
  SPIDataSet();
  //Check BLERecieve data
  BLERecieve();
  //fifo check initialize
  mpuFifoCheck = false;
  delay(10);
}

// ================================================================
// ===                       BUTTON Thread                      ===
// ================================================================

//menu button check
int menu() {
  if (menuSelect == 0 && buttonDown2Check == true) {
    freezeMenu = true;
  }
  button1();
  button2();
  button3();
  button4();
}

//about menu buttons(1.UP, 2.RESET, 3.OK, 4.DOWN)
void button1() {
  if (digitalRead(BUTTON1) == LOW) {
    buttonDown1++;
  }
  else if (digitalRead(BUTTON1) == HIGH && buttonDown1 > 2) {
    buttonDown1 = 0;
    buttonDown2Check = false;
    buttonDown3Check = false;   //buttondowns initialize
    menuSelect == 0 ? menuSelect = 8 : menuSelect--;
    mutes != true ? beefs++ : beefs;
  }
}
void button2() {
  if (digitalRead(BUTTON2) == LOW) {
    buttonDown2++;
  }
  else if (digitalRead(BUTTON2) == HIGH && buttonDown2 > 2) {
    buttonDown2 = 0;
    buttonDown2Check ? buttonDown2Check = false : buttonDown2Check = true;
    menuSelect >= 1 && menuSelect <= 7 ? BLEButtonTransfer() : menuSelect;
    mutes != true ? beefs++ : beefs;
  }
}
void button3() {
  if (digitalRead(BUTTON3) == LOW) {
    buttonDown3++;
  }
  else if (digitalRead(BUTTON3) == HIGH && buttonDown3 > 2) {
    buttonDown3 = 0;
    buttonDown3Check ? buttonDown3Check = false : buttonDown3Check = true;
    muteMusic();
  }
}
void button4() {
  if (digitalRead(BUTTON4) == LOW) {
    buttonDown4++;
  }
  else if (digitalRead(BUTTON4) == HIGH && buttonDown4 > 2) {
    buttonDown4 = 0;
    buttonDown2Check = false;
    buttonDown3Check = false;     //buttondowns initialize
    menuSelect >= 8 ? menuSelect = 0 : menuSelect++;
    mutes != true ? beefs++ : beefs;
  }
}

//about time change
int timeChange() {
  TChangeWhere == 0 ? TChangeWhere = 1 : TChangeWhere;
  TCButton1();
  TCButton2();
  TCButton3();
  TCButton4();
}

//about timeChange buttons(1.UP, 2.RESET, 3.OK, 4.DOWN)
void TCButton1() {
  if (digitalRead(BUTTON1) == LOW) {
    TCButtonDown1++;
  }
  else if (digitalRead(BUTTON1) == HIGH && TCButtonDown1 > 2) {
    TCButtonDown1 = 0;
    switch (TChangeWhere) {
      case 1:
        weekChange > 127 ? weekChange-- : weekChange++;
        weekChange == 127 ? weekChange = 0 : weekChange;
        break;
      case 2:
        yearChange > 127 ? yearChange-- : yearChange++;
        yearChange == 127 ? yearChange = 0 : yearChange;
        break;
      case 3:
        monthChange > 127 ? monthChange-- : monthChange++;
        monthChange == 127 ? monthChange = 0 : monthChange;
        break;
      case 4:
        dayChange > 127 ? dayChange-- : dayChange++;
        dayChange == 127 ? dayChange = 0 : dayChange;
        break;
      case 5:
        hourChange > 127 ? hourChange-- : hourChange++;
        hourChange == 127 ? hourChange = 0 : hourChange;
        break;
      case 6:
        minutsChange > 127 ? minutsChange-- : minutsChange++;
        minutsChange == 127 ? minutsChange = 0 : minutsChange;
        break;
      default:
        break;
    }
    mutes != true ? beefs++ : beefs;
  }
}
void TCButton2() {
  if (digitalRead(BUTTON2) == LOW) {
    TCButtonDown2++;
  }
  else if (digitalRead(BUTTON2) == HIGH && TCButtonDown2 > 2) {
    TCButtonDown2 = 0;
    TChangeWhere++;
    TChangeWhere > 8 ? TChangeWhere = 1 : TChangeWhere;  //initialize TChangeWhere
    mutes != true ? beefs++ : beefs;
  }
}
void TCButton3() {
  if (digitalRead(BUTTON3) == LOW) {
    TCButtonDown3++;
  }
  else if (digitalRead(BUTTON3) == HIGH && TCButtonDown3 > 2) {
    TCButtonDown3 = 0;
    if (TChangeWhere == 7) {
      TChangeWhere = 1;
      buttonDown2Check = false;
      buttonDown3Check = false;
      freezeMenu = false; //goto normal menu
    }
    else if (TChangeWhere == 8) {
      TChangeOK = 9;  //if TChangeOK is 9, Save sign.
      buttonDown2Check = false;
      buttonDown3Check = false; //goto normal menu
    }
    mutes != true ? beefs++ : beefs;
  }
}
void TCButton4() {
  if (digitalRead(BUTTON4) == LOW) {
    TCButtonDown4++;
  }
  else if (digitalRead(BUTTON4) == HIGH && TCButtonDown4 > 2) {
    TCButtonDown4 = 0;
    switch (TChangeWhere) {
      case 1:
        weekChange > 127 ? weekChange++ : weekChange--;
        weekChange == 255 ? weekChange = 128 : weekChange;
        break;
      case 2:
        yearChange > 127 ? yearChange++ : yearChange--;
        yearChange == 255 ? yearChange = 128 : yearChange;
        break;
      case 3:
        monthChange > 127 ? monthChange++ : monthChange--;
        monthChange == 255 ? monthChange = 128 : monthChange;
        break;
      case 4:
        dayChange > 127 ? dayChange++ : dayChange--;
        dayChange == 255 ? dayChange = 128 : dayChange;
        break;
      case 5:
        hourChange > 127 ? hourChange++ : hourChange--;
        hourChange == 255 ? hourChange = 128 : hourChange;
        break;
      case 6:
        minutsChange > 127 ? minutsChange++ : minutsChange--;
        minutsChange == 255 ? minutsChange = 128 : minutsChange;
        break;
      default:
        break;
    }
    mutes != true ? beefs++ : beefs;
  }
}

// ================================================================
// ===                     getMPUData Thread                    ===
// ================================================================

void MPU_fifoBuffer() {

  // if programming failed, don't try to do anything
  if (!dmpReady) return;

  // wait for MPU interrupt or extra packet(s) available
  while (!mpuInterrupt && fifoCount < packetSize) {
    // other program behavior stuff here
    // .
    // .
    // .
    // if you are really paranoid you can frequently test in between other
    // stuff to see if mpuInterrupt is true, and if so, "break;" from the
    // while() loop to immediately process the MPU data
    // .
    // .
    // .
  }

  // reset interrupt flag and get INT_STATUS byte
  mpuInterrupt = false;
  mpuIntStatus = mpu.getIntStatus();

  // get current FIFO count
  fifoCount = mpu.getFIFOCount();

  // check for overflow (this should never happen unless our code is too inefficient)
  if ((mpuIntStatus & 0x10) || fifoCount == 1024) {
    // reset so we can continue cleanly
    mpu.resetFIFO();
    Serial.println(F("FIFO overflow!"));

    // otherwise, check for DMP data ready interrupt (this should happen frequently)
  } else if (mpuIntStatus & 0x02) {
    // wait for correct available data length, should be a VERY short wait
    while (fifoCount < packetSize) fifoCount = mpu.getFIFOCount();

    // read a packet from FIFO
    mpu.getFIFOBytes(fifoBuffer, packetSize);

    // track FIFO count here in case there is > 1 packet available
    // (this lets us immediately read more without waiting for an interrupt)
    fifoCount -= packetSize;
    mpuFifoCheck = true;
  }
}

// ================================================================
// ===                    MAIN PROGRAM Thread                   ===
// ================================================================

//launch Passometer...need menu parameter, count, bigyo(...)
static uint8_t passometer() {
  uint32_t current_time = millis();

  // Euler angles in degrees
  mpu.dmpGetAccel(passAccel, fifoBuffer);

  //reset result
  boolean passCheck1 = abs(psFstX - (passAccel[0]  / 180)) > 20 || abs(psFstY - (passAccel[1]  / 180)) > 10 || abs(psFstZ - (passAccel[2]  / 180)) > 10;
  boolean passCheck2 = abs(psFstX - (passAccel[0]  / 180)) > 8 && abs(psFstY - (passAccel[1]  / 180)) > 4 && abs(psFstZ - (passAccel[2]  / 180)) > 4;
  boolean passCheck = passCheck1 && passCheck2;

  //passometerCount
  if (psRoofCount == false) {
    psFstX = passAccel[0]  / 180;
    psFstY = passAccel[1]  / 180;
    psFstZ = passAccel[2]  / 180;
    psRoofCount = true;
  }
  //TimeCheck
  if (current_time - pass_prev_time > 800) {
    if (passCheck) {
      psResult = true;
      pass_prev_time = current_time;
    }
  }
  //passmeterCheck
  if (psResult == true) {
    psResult = false;
    psRoofCount = false;
    psmeter++;
    psmeterTotal = psmeter;
  }
}



//launch distance by passometer...need menu parameter, count, bigyo(...)
static uint8_t distance() {
  boolean result = false;
  uint32_t current_time = millis();

  // Euler angles in degrees
  mpu.dmpGetAccel(distAccel, fifoBuffer);

  //reset PrevRunCheck
  boolean firstCheck1 = abs(distPrevX - (distAccel[0]  / 180)) > 20 || abs(distPrevY - (distAccel[1]  / 180)) > 10 || abs(distPrevZ - (distAccel[2]  / 180)) > 10;
  boolean firstCheck2 = abs(distPrevX - (distAccel[0]  / 180)) > 8 && abs(distPrevY - (distAccel[1]  / 180)) > 4 && abs(distPrevZ - (distAccel[2]  / 180)) > 4;
  boolean firstRunCheck = firstCheck1 && firstCheck2;
  boolean runCheck = distCount > 250;
  uint8_t runOK = abs(distAccel[2] / 180 - distPrevZ);

  //prev type save
  distPrevX = distAccel[0] / 180;
  distPrevY = distAccel[1] / 180;
  distPrevZ = distAccel[2] / 180;

  //Data checked;
  if (firstRunCheck && distCount == 0) {
    distStartCheck = true;
  }
  if (distStartCheck) {
    distCount = distCount + runOK;
  }

  if (runCheck) {
    if (current_time - run_prev_time > 250) {
      distStartCheck = false;
      result = true;
      run_prev_time = current_time;
    }
  }

  //final check
  if (result == 1) {
    result = false;
    distCount = 0;
    distPrevX = 0;
    distPrevY = 0;
    distPrevZ = 0;
    distmeter++;
    distmeterTotal = distmeter;
  }
}



//launch Passometer...need menu parameter, count, bigyo(...)
static uint8_t run_passometer() {
  uint32_t current_time = millis();

  //reset result
  boolean run_passCheck1 = abs(run_psFstX - (run_passAccel[0]  / 180)) > 20 || abs(run_psFstY - (run_passAccel[1]  / 180)) > 10 || abs(run_psFstZ - (run_passAccel[2]  / 180)) > 10;
  boolean run_passCheck2 = abs(run_psFstX - (run_passAccel[0]  / 180)) > 8 && abs(run_psFstY - (run_passAccel[1]  / 180)) > 4 && abs(run_psFstZ - (run_passAccel[2]  / 180)) > 4;
  boolean run_passCheck = run_passCheck1 && run_passCheck2;

  // Euler angles in degrees
  mpu.dmpGetAccel(run_passAccel, fifoBuffer);

  //passometerCount
  if (run_psRoofCount == false) {
    run_psFstX = run_passAccel[0]  / 180;
    run_psFstY = run_passAccel[1]  / 180;
    run_psFstZ = run_passAccel[2]  / 180;
    run_psRoofCount = true;
  }
  //TimeCheck
  if (current_time - run_pass_prev_time > 800) {
    if (run_passCheck) {
      run_psResult = true;
      run_pass_prev_time = current_time;
    }
  }
  //passmeterCheck
  if (run_psResult == true) {
    run_psResult = false;
    run_psRoofCount = false;
    run_psmeter++;
    run_psmeterTotal = run_psmeter;
  }
}



//launch runn by passometer...need menu parameter, count, bigyo(...)
static uint8_t run_distance() {
  boolean result = false;
  uint32_t current_time = millis();

  // Euler angles in degrees
  mpu.dmpGetAccel(run_distAccel, fifoBuffer);

  //reset PrevRunCheck
  boolean run_distCheck1 = abs(run_distPrevX - (run_distAccel[0]  / 180)) > 20 || abs(run_distPrevY - (run_distAccel[1]  / 180)) > 10 || abs(run_distPrevZ - (run_distAccel[2]  / 180)) > 10;
  boolean run_distCheck2 = abs(run_distPrevX - (run_distAccel[0]  / 180)) > 8 && abs(run_distPrevY - (run_distAccel[1]  / 180)) > 4 && abs(run_distPrevZ - (run_distAccel[2]  / 180)) > 4;
  boolean run_distRunCheck = run_distCheck1 && run_distCheck2;
  boolean run_distCheck = run_distCount > 250;
  uint8_t run_distOK = abs(run_distAccel[2] / 180 - run_distPrevZ);

  //prev type save
  run_distPrevX = run_distAccel[0] / 180;
  run_distPrevY = run_distAccel[1] / 180;
  run_distPrevZ = run_distAccel[2] / 180;

  //Data checked;
  if (run_distRunCheck && run_distCount == 0) {
    run_distStartCheck = true;
  }
  if (run_distStartCheck) {
    run_distCount = run_distCount + run_distOK;
  }

  if (run_distCheck) {
    if (current_time - run_dist_prev_time > 250) {
      run_distStartCheck = false;
      result = true;
      run_dist_prev_time = current_time;
    }
  }

  //final check
  if (result == 1) {
    result = false;
    run_distCount = 0;
    run_distPrevX = 0;
    run_distPrevY = 0;
    run_distPrevZ = 0;
    run_distmeter++;
    run_distmeterTotal = run_distmeter;
  }
}




//launch dumbbell...need menu parameter, count, bigyo(...)
static uint8_t dumbbell() {
  boolean result = false;
  uint32_t current_time = millis();

  // Euler angles in degrees
  mpu.dmpGetAccel(dumbAccel, fifoBuffer);

  //reset firstDumbCheck
  boolean firstDumbCheck = abs(dumbFstZ - dumbAccel[2] / 180) > 20;
  boolean dumbRunCheck = dumbRunCount > 30;
  uint8_t dumbRun = abs(dumbAccel[2] / 180 - dumbPrevZ);
  boolean lastDumbCheck = abs(dumbPrevZ - dumbAccel[2] / 180) < 2;

  //dumbbellCount
  if (dumbFstZ == 0) {
    dumbFstZ = dumbAccel[2] / 180;
  }

  //Data checked;
  if (firstDumbCheck && dumbRunCount == 0) {
    dumbStartCheck = true;
  }
  if (dumbStartCheck && dumbRunCount != 0 && dumbRun < 8) {
    dumbRunCount += dumbRun;
  }
  else if (dumbStartCheck && dumbRunCount == 0) {
    dumbRunCount = 1;
  }

  //prez type save
  dumbPrevZ = dumbAccel[2] / 180;

  if (dumbUpCheck && dumbRunCheck && lastDumbCheck) {
    if (current_time - dumb_prev_time > 500) {
      result = true;
      dumb_prev_time = current_time;
      dumbRunCount = 0;
    }
  }
  else if (dumbStartCheck && dumbRunCheck && lastDumbCheck) {
    dumbFstZ = 0;
    dumbUpCheck = true;
    dumbStartCheck = false;
    dumbRunCount = 0;
  }

  //final check
  if (result == 1) {
    result = false;
    dumbRunCount = 0;
    dumbUpCheck = false;
    dumbmeter++;
    dumbmeterTotal = dumbmeter;
  }
}



//launch barbell...need menu parameter, count, bigyo(...)
static uint8_t barbell() {
  boolean result = false;
  uint32_t current_time = millis();
  // Euler angles in degrees
  mpu.dmpGetAccel(barbAccel, fifoBuffer);

  //reset firstbarbCheck
  boolean firstBarbCheck = abs(barbFstZ - barbAccel[2] / 180) > 20;
  boolean barbRunCheck = barbRunCount > 30;
  uint8_t barbRun = abs(barbAccel[2] / 180 - barbPrevZ);
  boolean lastBarbCheck = abs(barbPrevZ - barbAccel[2] / 180) < 2;

  //barbbellCount
  if (barbFstZ == 0) {
    barbFstZ = barbAccel[2] / 180;
  }

  //Data checked;
  if (firstBarbCheck && barbRunCount == 0) {
    barbStartCheck = true;
  }
  if (barbStartCheck && barbRunCount != 0 && barbRun < 8) {
    barbRunCount += barbRun;
    //      Serial.print("1:");
    //      Serial.println(barbRunCount);
  }
  else if (barbStartCheck && barbRunCount == 0) {
    barbRunCount = 1;
    //      Serial.print("2:");
    //      Serial.println(barbRunCount);
  }

  //prez type save
  barbPrevZ = barbAccel[2] / 180;

  if (barbUpCheck && barbRunCheck && lastBarbCheck) {
    if (current_time - barb_prev_time > 500) {
      result = true;
      barb_prev_time = current_time;
      barbRunCount = 0;
    }
  }
  else if (barbStartCheck && barbRunCheck && lastBarbCheck) {
    barbFstZ = 0;
    barbUpCheck = true;
    barbStartCheck = false;
    barbRunCount = 0;
  }

  //final check
  if (result == 1) {
    barbRunCount = 0;
    barbUpCheck = false;
    barbmeter++;
    barbmeterTotal = barbmeter;
  }
}



//launch jumprope...need menu parameter, count, bigyo(...)
static uint8_t jumprope() {
  boolean result = false;
  uint32_t current_time = millis();

  // Euler angles in degrees
  mpu.dmpGetAccel(ropeAccel, fifoBuffer);

  //reset firstropeCheck
  boolean firstRopeCheck = abs(ropeFstZ - ropeAccel[2] / 180) > 10;
  boolean ropeRunCheck = ropeRunCount > 15;
  uint8_t ropeRun = abs(ropeAccel[2] / 180 - ropePrevZ);
  boolean lastRopeCheck = abs(ropePrevZ - ropeAccel[2] / 180) < 3;

  //ropebellCount
  if (ropeFstZ == 0) {
    ropeFstZ = ropeAccel[2] / 180;
  }

  //Data checked;
  if (firstRopeCheck && ropeRunCount == 0) {
    ropeStartCheck = true;
  }
  if (ropeStartCheck && ropeRunCount != 0 && ropeRun < 8) {
    ropeRunCount += ropeRun;
  }
  else if (ropeStartCheck && ropeRunCount == 0) {
    ropeRunCount = 1;
  }

  //prez type save
  ropePrevZ = ropeAccel[2] / 180;

  if (ropeUpCheck && ropeRunCheck && lastRopeCheck) {
    if (current_time - rope_prev_time > 250) {
      result = true;
      rope_prev_time = current_time;
      ropeRunCount = 0;
    }
  }
  else if (ropeStartCheck && ropeRunCheck && lastRopeCheck) {
    ropeFstZ = 0;
    ropeUpCheck = true;
    ropeStartCheck = false;
    ropeRunCount = 0;
  }

  //final check
  if (result == 1) {
    ropeRunCount = 0;
    ropeUpCheck = false;
    ropemeter++;
    ropemeterTotal = ropemeter;
  }
}



//about heartbeat
static uint8_t heartBeat() {
  uint32_t current_time = millis();         //  take a break
  if (current_time - prev_heartbeat_time > 80) {
    hbmeter = TotalBPM / 46;
    TotalBPM = 0;                              //hbmeter save and initialize

    if (QS == true) {    // A Heartbeat Was Found
      // BPM and IBI have been Determined
      // Quantified Self "QS" true when arduino finds a heartbeat
      fadeRate = 128;         // Makes the LED Fade Effect Happen
      // Set 'fadeRate' Variable to 255 to fade LED with pulse
      QS = false;                      // reset the Quantified Self flag for next time
      // Serial.println(hbmeter);        //print hbmeter parameter test
    }

    HBTotalCount();                    //calculate about 30minuts

    ledFadeToBeat();                      // Makes the LED Fade Effect Happen
    prev_heartbeat_time = current_time;
  }
}

void ledFadeToBeat() {
  fadeRate -= 15;                         //  set LED fade value
  fadeRate = constrain(fadeRate, 0, 255); //  keep LED fade value from going into negative numbers!
  analogWrite(fadePin, fadeRate);         //  fade LED
}

void HBTotalCount() {   //calculate hbmeterTotal
  uint32_t corrent_HBTimeCheck = millis();
  hbmeterAcc += hbmeter;
  hbmeterCount++;
  //save hbmeterTotal per minuts
  if (corrent_HBTimeCheck - prev_HBTimeCheck > 5) {
    hbmeterTotal[hbSequence] = hbmeterAcc / hbmeterCount;
    prev_HBTimeCheck = corrent_HBTimeCheck;
    hbSequence++;
    hbSequence > 6 ? hbSequence = 0 : hbSequence;
  }
}



//launch situp...need menu parameter, count, bigyo(...)
static uint8_t situp() {
  boolean result = false;
  uint32_t current_time = millis();

  // Euler angles in degrees
  mpu.dmpGetAccel(situpAccel, fifoBuffer);

  //reset firstsitupRunCountCheck
  boolean firstSitupCheck = abs(situpFstZ - situpAccel[2] / 180) > 20;
  boolean situpRunCheck = situpRunCount > 30;
  uint8_t situpRun = abs(situpAccel[2] / 180 - situpPrevZ);
  boolean lastSitupCheck = abs(situpPrevZ - situpAccel[2] / 180) < 2;

  //situpRunCount
  if (situpFstZ == 0) {
    situpFstZ = situpAccel[2] / 180;
  }

  //Data checked;
  if (firstSitupCheck && situpRunCount == 0) {
    situpStartCheck = true;
  }
  if (situpStartCheck && situpRunCount != 0 && situpRun < 8) {
    situpRunCount += situpRun;
  }
  else if (situpStartCheck && situpRunCount == 0) {
    situpRunCount = 1;
  }

  //prez type save
  situpPrevZ = situpAccel[2] / 180;

  if (situpUpCheck && situpRunCheck && lastSitupCheck) {
    if (current_time - dumb_prev_time > 500 && situpRunCount > 90) {
      result = true;
      situp_prev_time = current_time;
      situpRunCount = 0;
    }
  }
  else if (situpStartCheck && situpRunCheck && lastSitupCheck) {
    situpFstZ = 0;
    situpUpCheck = true;
    situpStartCheck = false;
    situpRunCount = 0;
  }

  //final check
  if (result == 1) {
    result = false;
    situpUpCheck = false;
    situpRunCount = 0;
    situpmeter++;
    situpmeterTotal = situpmeter;
  }
}



//launch sleep...need menu parameter, count, bigyo(...)
static uint8_t sleep() {
  shallowSleepmeter == 0 ? shallowSleepmeter = 1 : shallowSleepmeter;
  uint32_t current_time = millis();

  //reset result
  boolean deepCheck1 = abs(sleepPrevX - (sleepAccel[0]  / 180)) > 20 || abs(sleepPrevY - (sleepAccel[1]  / 180)) > 10 || abs(sleepPrevZ - (sleepAccel[2]  / 180)) > 10;
  boolean deepCheck2 = abs(sleepPrevX - (sleepAccel[0]  / 180)) > 8 && abs(sleepPrevY - (sleepAccel[1]  / 180)) > 4 && abs(sleepPrevZ - (sleepAccel[2]  / 180)) > 4;
  boolean deepCheck = deepCheck1 && deepCheck2;

  // Euler angles in degrees
  mpu.dmpGetAccel(sleepAccel, fifoBuffer);

  //Count
  sleepPrevX = sleepAccel[0]  / 180;
  sleepPrevY = sleepAccel[1]  / 180;
  sleepPrevZ = sleepAccel[2]  / 180;

  //TimeCheck
  if (current_time - sleep_prev_time > 1000) {
    deepCheck ? shallowSleepCount++ : deepSleepCount++;
    pass_prev_time = current_time;
  }
  if (shallowSleepCount + deepSleepCount > 60) {
    shallowSleepCount > deepSleepCount ? shallowSleepmeter++ : shallowSleepmeter;
    shallowSleepCount = 0;
    deepSleepCount = 0;
  }
}



//BLERecieve
void BLERecieve() {
  // put your main code here, to run repeatedly:
  current_BLE_time = millis();
  if (hm10.available()) {
    byte data = hm10.read();
    if ( true ) {
      intturuptCheck[0] = 1;
      BLECount = 0;
    }
    if ( data ==  '2' ) {
      intturuptCheck[1] = 1;
      mutes != true ? beefs++ : beefs;
      BLECount_M = 0;
    }
    if ( data ==  '3' ) {
      intturuptCheck[2] = 1;
      mutes != true ? beefs++ : beefs;
      BLECount_C = 0;
    }
    if ( data ==  '4' ) {
      intturuptCheck[3] = 1;
      mutes != true ? beefs++ : beefs;
      BLECount_K = 0;
    }
    Serial.write(data);
  }
  else if (BLECount > 180) {
    intturuptCheck[1] = 0;
    BLECount = 0;
  }
  else if (BLECount_M > 180) {
    intturuptCheck[2] = 0;
    BLECount_M = 0;
  }
  else if (BLECount_C > 180) {
    intturuptCheck[3] = 0;
    BLECount_C = 0;
  }
  else if (BLECount_K > 180) {
    intturuptCheck[4] = 0;
    BLECount_K = 0;
  }
  else {
    if (current_BLE_time - prev_BLE_time > 1000) {
      BLECount++;
      BLECount_M++;
      BLECount_C++;
      BLECount_K++;
      prev_BLE_time = current_BLE_time;
    }
  }
}



void BLEPassTransfer() {
  uint32_t reminder1 = psmeterTotal;
  uint32_t reminder2 = distmeterTotal;
  uint8_t servedPsmeter[6];
  uint8_t servedDistmeter[6];
  byte token = ':';

  //split data
  for (int i = 5; i >= 0; i--) {
    double temp = pow(10, i);
    servedPsmeter[5 - i] = reminder1 / temp;
    reminder1 = reminder1 % (int)temp;
  }
  for (int i = 5; i >= 0; i--) {
    double temp = pow(10, i);
    servedDistmeter[5 - i] = reminder2 / temp;
    reminder2 = reminder2 % (int)temp;
  }
  if (hm10.write((byte)'w') != 0) {
    psmeterTotal = 0;
    distmeterTotal = 0;
  }
  //transfer data
  for (int i = 0; i < 6; i++) {
    hm10.write((byte)servedPsmeter[i] + 48);
  }
  hm10.write((byte)token);
  for (int i = 0; i < 6; i++) {
    hm10.write((byte)servedDistmeter[i] + 48);
  }
  hm10.write((byte)'e');
}

//BLE, data transfer to android
void BLEHBTransfer() {
  uint16_t reminder1 = hbmeter_MAX();
  uint16_t reminder2 = hbmeter_MIN();
  uint8_t result = 0;
  uint8_t servedHbMaxmeter[3];
  uint8_t servedHbMinmeter[3];
  byte token = ':';

  //split data
  for (int i = 2; i >= 0; i--) {
    double temp = pow(10, i);
    servedHbMaxmeter[2 - i] = reminder1 / temp;
    reminder1 = reminder1 % (int)temp;
  }
  for (int i = 2; i >= 0; i--) {
    double temp = pow(10, i);
    servedHbMinmeter[2 - i] = reminder2 / temp;
    reminder2 = reminder2 % (int)temp;
  }
  if (hm10.write((byte)'h') != 0) {
    for (int i = 0; i < 6; i++) {
      hbmeterTotal[i] = 0;
    }
  }
  hm10.write((byte)token);
  for (int i = 0; i < 3; i++) {
    hm10.write((byte)servedHbMaxmeter[i] + 48);
  }
  hm10.write((byte)token);
  for (int i = 0; i < 3; i++) {
    hm10.write((byte)servedHbMinmeter[i] + 48);
  }
  hm10.write((byte)'e');
}

uint32_t hbmeter_MAX() {
  uint16_t hb_MAX = 0;
  for (int i = 0; i < 6; i++) {
    i == 0 || hb_MAX < hbmeterTotal[i] ? hb_MAX = hbmeterTotal[i] : hb_MAX;
  }
  return hb_MAX;
}
uint32_t hbmeter_MIN() {
  uint16_t hb_MIN = 0;
  for (int i = 0; i < 6; i++) {
    i == 0 || hb_MIN > hbmeterTotal[i] ? hb_MIN = hbmeterTotal[i] : hb_MIN;
  }
  return hb_MIN;
}



void BLESleepTransfer() {
  uint8_t reminder11 = shallowSleepmeter;
  uint8_t reminder12 = sleepFailCount;
  uint8_t servedShallowSleep[2];
  uint8_t servedFailCount[2];
  byte token = ':';

  for (int i = 1; i >= 0; i--) {
    double temp = pow(10, i);
    servedShallowSleep[1 - i] = reminder11 / temp;
    reminder11 = reminder11 % (int)temp;
  }
  for (int i = 1; i >= 0; i--) {
    double temp = pow(10, i);
    servedFailCount[1 - i] = reminder12 / temp;
    reminder12 = reminder12 % (int)temp;
  }
  if (hm10.write((byte)'a') == 0 ) {
    sleepFailCount++;
  }
  else {
    sleepFailCount = 0;
    shallowSleepmeter = 0;
  }
  for (int i = 0; i < 2; i++) {
    hm10.write((byte)servedShallowSleep[i] + 48);
  }
  hm10.write((byte)token);
  for (int i = 0; i < 2; i++) {
    hm10.write((byte)servedFailCount[i] + 48);
  }
  hm10.write((byte)'e');
  Serial.print("bo nam");
}



//BLE, data transfer to android
int BLEButtonTransfer() {
  uint32_t reminder1;
  uint32_t reminder2;
  uint8_t servedmeter[6];
  uint8_t servedRun_distmeter[6];
  byte token = ':';

  switch (menuSelect) {
    case 1:
      if (psmeterTotal != 0 || distmeterTotal != 0) {
        SPI_Tran_initializer();
      }
    case 2:
      if (run_psmeterTotal != 0 || run_distmeterTotal != 0) {
        reminder1 = run_psmeterTotal;
        reminder2 = run_distmeterTotal;
        //initialize data
        if (hm10.write((byte)'r') != 0 ) {
          SPI_Tran_initializer();
        }
        for (int i = 5; i >= 0; i--) {
          double temp = pow(10, i);
          servedmeter[5 - i] = reminder1 / temp;
          reminder1 = reminder1 % (int)temp;
        }
        for (int i = 5; i >= 0; i--) {
          double temp = pow(10, i);
          servedRun_distmeter[5 - i] = reminder2 / temp;
          reminder2 = reminder2 % (int)temp;
        }
        for (int i = 0; i < 6; i++) {
          hm10.write((byte)servedmeter[i] + 48);
        }
        hm10.write((byte)token);
        for (int i = 0; i < 6; i++) {
          hm10.write((byte)servedRun_distmeter[i] + 48);
        }
        hm10.write((byte)'e');
      }
      break;
    case 3:
      if (dumbmeterTotal != 0) {
        reminder1 = dumbmeterTotal;
        if (hm10.write((byte)'d') != 0 ) {
          SPI_Tran_initializer();
        }
      }
      break;
    case 4:
      if (barbmeterTotal != 0) {
        reminder1 = barbmeterTotal;
        if (hm10.write((byte)'b') != 0 ) {
          SPI_Tran_initializer();
        }
      }
      break;
    case 5:
      if (ropemeterTotal != 0) {
        reminder1 = ropemeterTotal;
        if (hm10.write((byte)'j') != 0 ) {
          SPI_Tran_initializer();
        }
      }

      break;
    case 7:
      if (situpmeterTotal != 0) {
        reminder1 = situpmeterTotal;
        if (hm10.write((byte)'s') != 0 ) {
          SPI_Tran_initializer();
        }
      }
      break;
    default:
      break;
  }
  //split data transfer data
  if ((menuSelect == 3 ||  menuSelect == 4 ||  menuSelect == 5 ||  menuSelect == 7) && (psmeterTotal + distmeterTotal + run_psmeterTotal + run_distmeterTotal + dumbmeterTotal + barbmeterTotal + ropemeterTotal + situpmeterTotal != 0)) {
    for (int i = 5; i >= 0; i--) {
      double temp = pow(10, i);
      servedmeter[5 - i] = reminder1 / temp;
      reminder1 = reminder1 % (int)temp;
    }
    for (int i = 0; i < 6; i++) {
      hm10.write((byte)servedmeter[i] + 48);
    }
    hm10.write((byte)'e');
  }
  return 1;
}

//BLE, data all transfer to android
void BLEAllTransfer() {
  uint32_t reminder1 = psmeterTotal;
  uint32_t reminder2 = distmeterTotal;
  uint32_t reminder3 = run_psmeterTotal;
  uint32_t reminder4 = run_distmeter;
  uint32_t reminder5 = dumbmeterTotal;
  uint32_t reminder6 = barbmeterTotal;
  uint32_t reminder7 = ropemeterTotal;
  uint32_t reminder8 = situpmeterTotal;
  uint32_t reminder9 = hbmeter_MAX();
  uint32_t reminder10 = hbmeter_MIN();
  uint8_t reminder11 = shallowSleepmeter;
  uint8_t reminder12 = sleepFailCount;
  uint8_t result = 0;
  uint8_t servedPsmeter[6];
  uint8_t servedDistmeter[6];
  uint8_t servedRun_Psmeter[6];
  uint8_t servedRun_distmeter[6];
  uint8_t servedDumbmeter[6];
  uint8_t servedBarbmeter[6];
  uint8_t servedRopemeter[6];
  uint8_t servedSitupmeter[6];
  uint8_t servedHbMaxmeter[3];
  uint8_t servedHbMinmeter[3];
  uint8_t servedShallowSleep[2];
  uint8_t servedFailCount[2];
  byte token = ':';

  //split data
  for (int i = 5; i >= 0; i--) {
    double temp = pow(10, i);
    servedPsmeter[5 - i] = reminder1 / temp;
    reminder1 = reminder1 % (int)temp;
  }
  for (int i = 5; i >= 0; i--) {
    double temp = pow(10, i);
    servedDistmeter[5 - i] = reminder2 / temp;
    reminder2 = reminder2 % (int)temp;
  }
  for (int i = 5; i >= 0; i--) {
    double temp = pow(10, i);
    servedRun_Psmeter[5 - i] = reminder3 / temp;
    reminder3 = reminder3 % (int)temp;
  }
  for (int i = 5; i >= 0; i--) {
    double temp = pow(10, i);
    servedRun_distmeter[5 - i] = reminder4 / temp;
    reminder4 = reminder4 % (int)temp;
  }
  for (int i = 5; i >= 0; i--) {
    double temp = pow(10, i);
    servedDumbmeter[5 - i] = reminder5 / temp;
    reminder5 = reminder5 % (int)temp;
  }
  for (int i = 5; i >= 0; i--) {
    double temp = pow(10, i);
    servedBarbmeter[5 - i] = reminder6 / temp;
    reminder6 = reminder6 % (int)temp;
  }
  for (int i = 5; i >= 0; i--) {
    double temp = pow(10, i);
    servedRopemeter[5 - i] = reminder7 / temp;
    reminder7 = reminder7 % (int)temp;
  }
  for (int i = 5; i >= 0; i--) {
    double temp = pow(10, i);
    servedSitupmeter[5 - i] = reminder8 / temp;
    reminder8 = reminder8 % (int)temp;
  }
  for (int i = 2; i >= 0; i--) {
    double temp = pow(10, i);
    servedHbMaxmeter[2 - i] = reminder9 / temp;
    reminder9 = reminder9 % (int)temp;
  }
  for (int i = 2; i >= 0; i--) {
    double temp = pow(10, i);
    servedHbMinmeter[2 - i] = reminder10 / temp;
    reminder10 = reminder10 % (int)temp;
  }
  for (int i = 1; i >= 0; i--) {
    double temp = pow(10, i);
    servedShallowSleep[1 - i] = reminder11 / temp;
    reminder11 = reminder11 % (int)temp;
  }
  for (int i = 1; i >= 0; i--) {
    double temp = pow(10, i);
    servedFailCount[1 - i] = reminder12 / temp;
    reminder12 = reminder12 % (int)temp;
  }
  

  //transfer data
  hm10.write((byte)'w');
  for (int i = 0; i < 6; i++) {
    hm10.write((byte)servedPsmeter[i] + 48);
  }
  hm10.write((byte)token);
  for (int i = 0; i < 6; i++) {
    hm10.write((byte)servedDistmeter[i] + 48);
  }
  hm10.write((byte)'e');
  delay(20);
  hm10.write((byte)'r');
  for (int i = 0; i < 6; i++) {
    hm10.write((byte)servedRun_Psmeter[i] + 48);
  }
  hm10.write((byte)token);
  for (int i = 0; i < 6; i++) {
    hm10.write((byte)servedRun_distmeter[i] + 48);
  }
  hm10.write((byte)'e');
  delay(20);
  hm10.write((byte)'d');
  for (int i = 0; i < 6; i++) {
    hm10.write((byte)servedDumbmeter[i] + 48);
  }
  hm10.write((byte)'e');
  delay(20);
  hm10.write((byte)'b');
  for (int i = 0; i < 6; i++) {
    hm10.write((byte)servedBarbmeter[i] + 48);
  }
  hm10.write((byte)'e');
  delay(20);
  hm10.write((byte)'j');
  for (int i = 0; i < 6; i++) {
    hm10.write((byte)servedRopemeter[i] + 48);
  }
  hm10.write((byte)'e');
  delay(20);
  hm10.write((byte)'s');
  for (int i = 0; i < 6; i++) {
    hm10.write((byte)servedSitupmeter[i] + 48);
  }
  hm10.write((byte)'e');
  delay(20);
  hm10.write((byte)'h');
  for (int i = 0; i < 3; i++) {
    hm10.write((byte)servedHbMaxmeter[i] + 48);
  }
  hm10.write((byte)token);
  for (int i = 0; i < 3; i++) {
    hm10.write((byte)servedHbMinmeter[i] + 48);
  }
  hm10.write((byte)'e');
  delay(20);
  hm10.write((byte)'a');
  for (int i = 0; i < 2; i++) {
    hm10.write((byte)servedShallowSleep[i] + 48);
  }
  hm10.write((byte)'e');
  Serial.print("bo nam");
}

//initialize data.
void SPI_Tran_initializer() {
  switch (menuSelect) {
    case 1:
      psmeter = 0;
      distmeter = 0;
      break;
    case 2:
      run_psmeter = 0;
      run_psmeterTotal = 0;
      run_distmeter = 0;
      run_distmeterTotal = 0;
      break;
    case 3:
      dumbmeter = 0;
      dumbmeterTotal = 0;
      break;
    case 4:
      barbmeter = 0;
      barbmeterTotal = 0;
      break;
    case 5:
      ropemeter = 0;
      ropemeterTotal = 0;
      break;
    case 7:
      situpmeter = 0;
      situpmeterTotal = 0;
      break;
    default:
      break;
  }
}

//Ready for SPIDataSettings
void SPIDataSet() {
  byte servedData[8] = {0,};
  uint32_t reminder = 0;
  uint8_t result = 0;

  //menu check (menu += 16);
  switch (menuSelect) {
    case 0:
      freezeMenu ? servedData[0] += 240 : servedData[0] += 16;
      break;
    case 1:
      servedData[0] += 32;
      reminder = psmeter;
      break;
    case 2:
      servedData[0] += 48;
      reminder = run_distmeter;
      break;
    case 3:
      servedData[0] += 64;
      reminder = dumbmeter;
      break;
    case 4:
      servedData[0] += 80;
      reminder = barbmeter;
      break;
    case 5:
      servedData[0] += 96;
      reminder = ropemeter;
      break;
    case 6:
      servedData[0] += 112;
      reminder = hbmeter;
      break;
    case 7:
      servedData[0] += 128;
      reminder = situpmeter;
      break;
    case 8:
      servedData[0] += 144;
      reminder = 0;
      break;
    default:
      break;
  }
  //inturruptMenu(sequence[n] = BMCK)
  intturuptCheck[0] == 1 ? servedData[0] += 8 : servedData[0];
  intturuptCheck[1] == 1 ? servedData[0] += 4 : servedData[0];
  intturuptCheck[2] == 1 ? servedData[0] += 2 : servedData[0];
  intturuptCheck[3] == 1 ? servedData[0] += 1 : servedData[0];

  //dataSave
  for (int i = 5; i >= 0; i--) {
    double temp = pow(10, i);
    servedData[6 - i] = reminder / temp;
    reminder = reminder % (int)temp;
  }
  if (freezeMenu == true) {
    servedData[1] = weekChange;
    servedData[2] = yearChange;
    servedData[3] = monthChange;
    servedData[4] = dayChange;
    servedData[5] = hourChange;
    servedData[6] = minutsChange;
    servedData[7] = (TChangeOK == 9 ? TChangeOK : TChangeWhere);
  }

  //SaveSPIData
  for (int i = 0; i < 8; i++) {
    SPIData[i] = servedData[i];
  }
}

void muteMusic() {
  mutes == false ? mutes = true : mutes = false;
}

//When SPI Master Require Initialize...do it, all initialize.
void initializer() {
  psmeter = 0;
  psmeterTotal = 0;
  distmeter = 0;
  distmeterTotal = 0;
  run_psmeter = 0;
  run_psmeterTotal = 0;
  run_distmeter = 0;
  run_distmeterTotal = 0;
  dumbmeter = 0;
  dumbmeterTotal = 0;
  barbmeter = 0;
  barbmeterTotal = 0;
  ropemeter = 0;
  ropemeterTotal = 0;
  situpmeter = 0;
  situpmeterTotal = 0;
  shallowSleepmeter = 0;
  for (int i = 0; i < 6; i++) {
    hbmeterTotal[i] = 0;
  }
}

//time change parameter initialize when SPITransfer;
void initTimeChange() {
  weekChange = 0;
  yearChange = 0;
  monthChange = 0;
  dayChange = 0;
  hourChange = 0;
  minutsChange = 0;
}

