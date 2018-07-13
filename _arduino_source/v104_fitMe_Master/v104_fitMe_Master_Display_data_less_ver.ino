#include <stdio.h>
#include <stdlib.h>
#include <String.h>
#include "U8glib.h"
#include "bitmap.h"
#include <SPI.h>
#include <DS1302.h>

//display
U8GLIB_SH1106_128X64 u8g(7, 6, 5, 3);  // CLK MOSI CS DC

//RTC
DS1302 rtc(2, 8, 9); //ce, io, sclk

//SPI
int received[8];
int total;
char total_dr[7];
int radix = 10;

uint8_t shour;
uint8_t smin;
uint8_t ssec;
uint8_t* sdow;
uint8_t smon;
uint8_t sdate;
uint16_t syear;

char *vidow;
int downum;

int sd;
int bc; // 버튼 체크 값
int ac; // 버튼 체크 값

int acc_check;

// 시간, 전압, 비프음
uint32_t current_time;
uint32_t save_time;
uint32_t prev_stime;
int save_check = 0;
int a;
int count;
int vcc_result = 0;
int speakerpin = 4;
String output; //date
String output2; //time

////// draws()
// draw clock
// opening logo
void opening_logo(void) {
  while (a < 143) {
    u8g.drawBitmapP( 168 - a, 22, 4, 32, footprint);
    u8g.setFont(u8g_font_helvB12);
    u8g.drawStr(58, 42, "Fit Me!");
    break;
  }
  if (a >= 143) delay(50);
}

void clock_dr(void) {
  u8g.setFont(u8g_font_6x13);
  u8g.drawStr(0, 32, rtc.getDOWStr());
  u8g.drawStr(62, 32, rtc.getDateStr());
  u8g.drawStr(0, 48, rtc.getTimeStr());
  if (save_check == 1) {
    u8g.setFont(u8g_font_u8glib_4);
    u8g.drawStr(99, 64, "saved!!");
    save_time = millis();
    if ((save_time - prev_stime) > 2000) {
      save_check = 0;
    }
  }
}

void mini_clock_dr(void) {
  u8g.setFont(u8g_font_u8glib_4);
  u8g.drawStr(82, 10, rtc.getTimeStr());

}

void battery_dr(void) {
  if (vcc_result > 4100 ) {
    u8g.drawBitmapP( 112, 0, 2, 16, battery_full_bitmap);
  }
  else if (vcc_result > 3000 & vcc_result < 4100) {
    u8g.drawBitmapP( 112, 0, 2, 16, battery_2_bitmap);
  }
  else if (vcc_result > 2000  & vcc_result < 3000) {
    u8g.drawBitmapP( 112, 0, 2, 16, battery_1_bitmap);
  }
  else {
    u8g.drawBitmapP( 112, 0, 2, 16, battery_0_bitmap);
  }
}

void bluetooth_dr(void) {
  u8g.drawBitmapP( 0, 0, 1, 16, bluetooth_bitmap);
}

void message_dr(void) {
  u8g.drawBitmapP( 30, 0, 2, 16, msg_bitmap);
}

void call_dr(void) {
  u8g.drawBitmapP( 48, 0, 2, 16, phonecall_bitmap);
}

void kakao_dr(void) {
  u8g.drawBitmapP( 64, 0, 2, 16, kakao_bitmap);
}

// function draws
void walk_dr(void) {
  u8g.drawBitmapP( 24, 24, 4, 32, footprint);
  u8g.setFont(u8g_font_helvB12);
  u8g.drawStr(72, 48, total_dr);
}

void dumbbell_dr(void) {
  u8g.drawBitmapP( 20, 30, 3, 24, dumbbell);
  u8g.setFont(u8g_font_helvB12);
  u8g.drawStr(72, 48, total_dr);
  // u8g.drawStr(80, 48, "/ 15");
}

void barbell_dr(void) {
  u8g.drawBitmapP( 20, 30, 3, 24, barbell);
  u8g.setFont(u8g_font_helvB12);
  u8g.drawStr(72, 48, total_dr);
  // u8g.drawStr(80, 48, "/ 15");
}

void jumprope_dr(void) {
  u8g.drawBitmapP( 20, 30, 3, 24, jumprope);
  u8g.setFont(u8g_font_helvB12);
  u8g.drawStr(72, 48, total_dr);
  //u8g.drawStr(80, 48, "/ 100");
}

void running_dr(void) {
  u8g.drawBitmapP( 20, 30, 3, 24, running_bitmap);
  u8g.setFont(u8g_font_helvB12);
  u8g.drawStr(60, 48, total_dr);
  u8g.drawStr(88, 48, " m");
}

void heartbeat_dr(void) {
  u8g.drawBitmapP( 24, 24, 4, 32, heartbeat);
  u8g.setFont(u8g_font_helvB12);
  u8g.drawStr(72, 48, total_dr);
}

void temperature_dr(void) {
  u8g.drawBitmapP( 24, 24, 4, 32, temperature);
  u8g.setFont(u8g_font_helvB12);
  u8g.drawStr(72, 48, total_dr);
  //u8g.drawStr(88, 48, "'C");
}

void sleep_dr(void) {
  u8g.drawBitmapP( 45, 24, 4, 32, sleep);
  u8g.setFont(u8g_font_helvB12);
  u8g.drawStr(120, 48, " ");
}

// 요일 변경 함수
void in_sdow() {
  if (downum == 7) {
    *sdow = "SUNDAY";
  } else if (downum == 1) {
    *sdow = "MONDAY";
  } else if (downum == 2) {
    *sdow = "TUESDAY";
  } else if (downum == 3) {
    *sdow = "WEDNESDAY";
  } else if (downum == 4) {
    *sdow = "THURSDAY";
  } else if (downum == 5) {
    *sdow = "FRIDAY";
  } else if (downum == 6) {
    *sdow = "SATURDAY";
  }
}

void in_vidow() {
  if (vidow == "Sunday") {
    downum = 7;
  } else if (vidow == "Monday") {
    downum = 1;
  } else if (vidow == "Tuesday") {
    downum = 2;
  } else if (vidow == "Wednesday") {
    downum = 3;
  } else if (vidow == "Thursday") {
    downum = 4;
  } else if (vidow == "Friday") {
    downum = 5;
  } else if (vidow == "Saturday") {
    downum = 6;
  }
}



// 시간 변수 입력 함수
void insert_time() {
  String yearb, monb, dateb, hourb, minb, secb;//
  output = rtc.getDateStr();
  output = output.substring(0, 10);
  output2 = rtc.getTimeStr();
  output2 = output2.substring(0, 10);

  yearb = output.substring(0, 4);
  syear = atoi(yearb.c_str());
  //Serial.print("test syear : ");
  //Serial.println(syear);

  monb = output.substring(5, 7);
  smon = atoi(monb.c_str());
  //Serial.print("test smon : ");
  //Serial.println(smon);

  dateb = output.substring(8, 10);
  sdate = atoi(dateb.c_str());
  //Serial.print("test sdate : ");
  //Serial.println(sdate);

  hourb = output2.substring(0, 2);
  shour = atoi(hourb.c_str());
  //Serial.print("test shour : ");
  //Serial.println(shour);

  minb = output2.substring(3, 5);
  smin = atoi(minb.c_str());
  // Serial.print("test smin : ");
  // Serial.println(smin);

  secb = output2.substring(6, 8);
  ssec = atoi(secb.c_str());
  //Serial.print("test ssec : ");
  //Serial.println(ssec);
}

// 시간 변경 시 호출하는 함수
void retime_dr() {

  char cu[3];
  char du[3];
  char eu[3];
  char fu[3];
  char gu[3];
  char au[3];
  char bu[5];

  if (received[7] != 9) {
    insert_time();
    in_vidow();
  }

  // Time Edit 변경항목 호출
  if (received[7] != 0) {
    if (received[7] == 1) {
      downum = downum + (( received[1] > 127 ) ? (received[1] * (-1) + 128) % 7 : received[1]);
      if (downum >= 8 | downum <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(11);
        digitalWrite(SS, HIGH);
      }
      syear = syear + (( received[2] > 127 ) ? received[2] * (-1) + 128 : received[2]);
      smon = smon + (( received[3] > 127 ) ? (received[3] * (-1) + 128) % 12 : received[3]);
      if (smon >= 13 | smon <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(12);
        digitalWrite(SS, HIGH);
      }
      sdate = sdate + (( received[4] > 127 ) ? (received[4] * (-1) + 128) % 31 : received[4]);
      if (sdate >= 32 | sdate <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(13);
        digitalWrite(SS, HIGH);
      }
      shour = shour + (( received[5] > 127 ) ? (received[5] * (-1) + 128) % 24 : received[5]);
      if (shour >= 24 | shour <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(14);
        digitalWrite(SS, HIGH);
      }
      smin = smin + (( received[6] > 127 ) ? (received[6] * (-1) + 128) % 60 : received[6]);
      if (smin >= 60 | smin <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(15);
        digitalWrite(SS, HIGH);
      }
      //downum == 7 ? downum = 0 : downum;
      u8g.drawBitmapP( 0, 24, 1, 8, select_bitmap);
    } else if (received[7] == 2) {
      downum = downum + (( received[1] > 127 ) ? (received[1] * (-1) + 128) % 7 : received[1]);
      if (downum >= 8 | downum <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(11);
        digitalWrite(SS, HIGH);
      }
      syear = syear + (( received[2] > 127 ) ? received[2] * (-1) + 128 : received[2]);
      smon = smon + (( received[3] > 127 ) ? (received[3] * (-1) + 128) % 12 : received[3]);
      if (smon >= 13 | smon <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(12);
        digitalWrite(SS, HIGH);
      }
      sdate = sdate + (( received[4] > 127 ) ? (received[4] * (-1) + 128) % 31 : received[4]);
      if (sdate >= 32 | sdate <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(13);
        digitalWrite(SS, HIGH);
      }
      shour = shour + (( received[5] > 127 ) ? (received[5] * (-1) + 128) % 24 : received[5]);
      if (shour >= 24 | shour <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(14);
        digitalWrite(SS, HIGH);
      }
      smin = smin + (( received[6] > 127 ) ? (received[6] * (-1) + 128) % 60 : received[6]);
      if (smin >= 60 | smin <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(15);
        digitalWrite(SS, HIGH);
      }
      //syear > 3000 ? syear = 2018 : syear;
      u8g.drawBitmapP( 57, 24, 1, 8, select_bitmap);
    } else if (received[7] == 3) {
      downum = downum + (( received[1] > 127 ) ? (received[1] * (-1) + 128) % 7 : received[1]);
      if (downum >= 8 | downum <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(11);
        digitalWrite(SS, HIGH);
      }
      syear = syear + (( received[2] > 127 ) ? received[2] * (-1) + 128 : received[2]);
      smon = smon + (( received[3] > 127 ) ? (received[3] * (-1) + 128) % 12 : received[3]);
      if (smon >= 13 | smon <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(12);
        digitalWrite(SS, HIGH);
      }
      sdate = sdate + (( received[4] > 127 ) ? (received[4] * (-1) + 128) % 31 : received[4]);
      if (sdate >= 32 | sdate <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(13);
        digitalWrite(SS, HIGH);
      }
      shour = shour + (( received[5] > 127 ) ? (received[5] * (-1) + 128) % 24 : received[5]);
      if (shour >= 24 | shour <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(14);
        digitalWrite(SS, HIGH);
      }
      smin = smin + (( received[6] > 127 ) ? (received[6] * (-1) + 128) % 60 : received[6]);
      if (smin >= 60 | smin <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(15);
        digitalWrite(SS, HIGH);
      }
      //smon > 12 ? smon = 1 : smon;
      u8g.drawBitmapP( 89, 24, 1, 8, select_bitmap);
    } else if (received[7] == 4) {
      downum = downum + (( received[1] > 127 ) ? (received[1] * (-1) + 128) % 7 : received[1]);
      if (downum >= 8 | downum <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(11);
        digitalWrite(SS, HIGH);
      }
      syear = syear + (( received[2] > 127 ) ? received[2] * (-1) + 128 : received[2]);
      smon = smon + (( received[3] > 127 ) ? (received[3] * (-1) + 128) % 12 : received[3]);
      if (smon >= 13 | smon <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(12);
        digitalWrite(SS, HIGH);
      }
      sdate = sdate + (( received[4] > 127 ) ? (received[4] * (-1) + 128) % 31 : received[4]);
      if (sdate >= 32 | sdate <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(13);
        digitalWrite(SS, HIGH);
      }
      shour = shour + (( received[5] > 127 ) ? (received[5] * (-1) + 128) % 24 : received[5]);
      if (shour >= 24 | shour <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(14);
        digitalWrite(SS, HIGH);
      }
      smin = smin + (( received[6] > 127 ) ? (received[6] * (-1) + 128) % 60 : received[6]);
      if (smin >= 60 | smin <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(15);
        digitalWrite(SS, HIGH);
      }
      //sdate > 31 ? sdate = 1 : sdate;
      u8g.drawBitmapP( 104, 24, 1, 8, select_bitmap);
    } else if (received[7] == 5) {
      downum = downum + (( received[1] > 127 ) ? (received[1] * (-1) + 128) % 7 : received[1]);
      if (downum >= 8 | downum <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(11);
        digitalWrite(SS, HIGH);
      }
      syear = syear + (( received[2] > 127 ) ? received[2] * (-1) + 128 : received[2]);
      smon = smon + (( received[3] > 127 ) ? (received[3] * (-1) + 128) % 12 : received[3]);
      if (smon >= 13 | smon <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(12);
        digitalWrite(SS, HIGH);
      }
      sdate = sdate + (( received[4] > 127 ) ? (received[4] * (-1) + 128) % 31 : received[4]);
      if (sdate >= 32 | sdate <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(13);
        digitalWrite(SS, HIGH);
      }
      shour = shour + (( received[5] > 127 ) ? (received[5] * (-1) + 128) % 24 : received[5]);
      if (shour >= 24 | shour <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(14);
        digitalWrite(SS, HIGH);
      }
      smin = smin + (( received[6] > 127 ) ? (received[6] * (-1) + 128) % 60 : received[6]);
      if (smin >= 60 | smin <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(15);
        digitalWrite(SS, HIGH);
      }
      //shour > 24 ? shour = 0 : shour;
      u8g.drawBitmapP( 0, 40, 1, 8, select_bitmap);
    } else if (received[7] == 6) {
      downum = downum + (( received[1] > 127 ) ? (received[1] * (-1) + 128) % 7 : received[1]);
      if (downum >= 8 | downum <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(11);
        digitalWrite(SS, HIGH);
      }
      syear = syear + (( received[2] > 127 ) ? received[2] * (-1) + 128 : received[2]);
      smon = smon + (( received[3] > 127 ) ? (received[3] * (-1) + 128) % 12 : received[3]);
      if (smon >= 13 | smon <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(12);
        digitalWrite(SS, HIGH);
      }
      sdate = sdate + (( received[4] > 127 ) ? (received[4] * (-1) + 128) % 31 : received[4]);
      if (sdate >= 32 | sdate <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(13);
        digitalWrite(SS, HIGH);
      }
      shour = shour + (( received[5] > 127 ) ? (received[5] * (-1) + 128) % 24 : received[5]);
      if (shour >= 24 | shour <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(14);
        digitalWrite(SS, HIGH);
      }
      smin = smin + (( received[6] > 127 ) ? (received[6] * (-1) + 128) % 60 : received[6]);
      if (smin >= 60 | smin <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(15);
        digitalWrite(SS, HIGH);
      }
      //smin > 60 ? smin = 0 : smin;
      u8g.drawBitmapP( 18, 40, 1, 8, select_bitmap);
    } else if (received[7] == 7) {
      downum = downum + (( received[1] > 127 ) ? (received[1] * (-1) + 128) % 7 : received[1]);
      if (downum >= 8 | downum <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(11);
        digitalWrite(SS, HIGH);
      }
      syear = syear + (( received[2] > 127 ) ? received[2] * (-1) + 128 : received[2]);
      smon = smon + (( received[3] > 127 ) ? (received[3] * (-1) + 128) % 12 : received[3]);
      if (smon >= 13 | smon <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(12);
        digitalWrite(SS, HIGH);
      }
      sdate = sdate + (( received[4] > 127 ) ? (received[4] * (-1) + 128) % 31 : received[4]);
      if (sdate >= 32 | sdate <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(13);
        digitalWrite(SS, HIGH);
      }
      shour = shour + (( received[5] > 127 ) ? (received[5] * (-1) + 128) % 24 : received[5]);
      if (shour >= 24 | shour <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(14);
        digitalWrite(SS, HIGH);
      }
      smin = smin + (( received[6] > 127 ) ? (received[6] * (-1) + 128) % 60 : received[6]);
      if (smin >= 60 | smin <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(15);
        digitalWrite(SS, HIGH);
      }
      u8g.drawBitmapP( 58, 42, 1, 8, select_bitmap);
    } else if (received[7] == 8) {
      downum = downum + (( received[1] > 127 ) ? (received[1] * (-1) + 128) % 7 : received[1]);
      if (downum >= 8 | downum <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(11);
        digitalWrite(SS, HIGH);
      }
      syear = syear + (( received[2] > 127 ) ? received[2] * (-1) + 128 : received[2]);
      smon = smon + (( received[3] > 127 ) ? (received[3] * (-1) + 128) % 12 : received[3]);
      if (smon >= 13 | smon <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(12);
        digitalWrite(SS, HIGH);
      }
      sdate = sdate + (( received[4] > 127 ) ? (received[4] * (-1) + 128) % 31 : received[4]);
      if (sdate >= 32 | sdate <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(13);
        digitalWrite(SS, HIGH);
      }
      shour = shour + (( received[5] > 127 ) ? (received[5] * (-1) + 128) % 24 : received[5]);
      if (shour >= 24 | shour <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(14);
        digitalWrite(SS, HIGH);
      }
      smin = smin + (( received[6] > 127 ) ? (received[6] * (-1) + 128) % 60 : received[6]);
      if (smin >= 60 | smin <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(15);
        digitalWrite(SS, HIGH);
      }
      u8g.drawBitmapP( 92, 42, 1, 8, select_bitmap);
    } else if (received[7] == 9 && received[7] != acc_check) {
      downum = downum + (( received[1] > 127 ) ? (received[1] * (-1) + 128) % 7 : received[1]);
      if (downum >= 8 | downum <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(11);
        digitalWrite(SS, HIGH);
      }
      syear = syear + (( received[2] > 127 ) ? received[2] * (-1) + 128 : received[2]);
      smon = smon + (( received[3] > 127 ) ? (received[3] * (-1) + 128) % 12 : received[3]);
      if (smon >= 13 | smon <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(12);
        digitalWrite(SS, HIGH);
      }
      sdate = sdate + (( received[4] > 127 ) ? (received[4] * (-1) + 128) % 31 : received[4]);
      if (sdate >= 32 | sdate <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(13);
        digitalWrite(SS, HIGH);
      }
      shour = shour + (( received[5] > 127 ) ? (received[5] * (-1) + 128) % 24 : received[5]);
      if (shour >= 24 | shour <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(14);
        digitalWrite(SS, HIGH);
      }
      smin = smin + (( received[6] > 127 ) ? (received[6] * (-1) + 128) % 60 : received[6]);
      if (smin >= 60 | smin <= -1 ) {
        digitalWrite(SS, LOW); // 슬레이브 선택
        SPI.transfer(15);
        digitalWrite(SS, HIGH);
      }
      time_save();
      save_time = millis();
      prev_stime = save_time;
      save_check = 1;
    }
  }
  // 변경내용 적용

  char *ssyear = itoa(syear, bu, 10);
  char *ssmon = itoa(smon, cu, 10);
  char *ssdate = itoa(sdate, du, 10);
  char *sshour = itoa(shour, eu, 10);
  char *ssmin = itoa(smin, fu, 10);
  char *sssec = itoa(ssec, gu, 10);
  char *ddownum = itoa(downum, au, 10);

  u8g.setFont(u8g_font_6x13);
  u8g.drawStr(6, 32, ddownum);
  u8g.drawStr(64, 32, ssyear);
  u8g.drawStr(97, 32, ssmon);
  u8g.drawStr(112, 32, ssdate);
  u8g.drawStr(6, 48, sshour);
  u8g.drawStr(24, 48, ssmin);
  u8g.drawStr(38, 48, sssec);
  // u8g.drawStr(38, 48, "00");
  u8g.setFont(u8g_font_u8glib_4);
  u8g.drawStr(64, 48, "CANCLE");
  u8g.drawStr(99, 48, "APPLY");
  u8g.drawStr(35, 64, "<Edit Time info>");

  acc_check = received[7];

}

////////////////

void initial_display_dr(void) {
  if (bc != ac) {
    tone(speakerpin, 3520, 50);
    ac = bc;
  }
  // 상황별 화면처리
  if (received[0] == 16) {
    clock_dr();
  } else if (received[0] == 17) {
    kakao_dr();
    clock_dr();
  } else if (received[0] == 18) {
    call_dr();
    clock_dr();
  } else if (received[0] == 19) {
    call_dr();
    kakao_dr();
    clock_dr();
  } else if (received[0] == 20) {
    message_dr();
    clock_dr();
  } else if (received[0] == 21) {
    message_dr();
    kakao_dr();
    clock_dr();
  } else if (received[0] == 22) {
    message_dr();
    call_dr();
    clock_dr();
  } else if (received[0] == 23) {
    message_dr();
    call_dr();
    kakao_dr();
    clock_dr();
  } else if (received[0] == 24) {
    bluetooth_dr();
    clock_dr();
  } else if (received[0] == 25) {
    bluetooth_dr();
    kakao_dr();
    clock_dr();
  } else if (received[0] == 26) {
    bluetooth_dr();
    call_dr();
    clock_dr();
  } else if (received[0] == 27) {
    bluetooth_dr();
    call_dr();
    kakao_dr();
    clock_dr();
  } else if (received[0] == 28) {
    bluetooth_dr();
    message_dr();
    clock_dr();
  } else if (received[0] == 29) {
    bluetooth_dr();
    message_dr();
    kakao_dr();
    clock_dr();
  } else if (received[0] == 30) {
    bluetooth_dr();
    message_dr();
    call_dr();
    clock_dr();
  } else if (received[0] == 31) {
    bluetooth_dr();
    message_dr();
    call_dr();
    kakao_dr();
    clock_dr();
  } else if (received[0] == 32) {
    walk_dr();
  } else if (received[0] == 33) {
    kakao_dr();
    walk_dr();
  } else if (received[0] == 34) {
    call_dr();
    walk_dr();
  } else if (received[0] == 35) {
    call_dr();
    kakao_dr();
    walk_dr();
  } else if (received[0] == 36) {
    message_dr();
    walk_dr();
  } else if (received[0] == 37) {
    message_dr();
    kakao_dr();
    walk_dr();
  } else if (received[0] == 38) {
    message_dr();
    call_dr();
    walk_dr();
  } else if (received[0] == 39) {
    message_dr();
    call_dr();
    kakao_dr();
    walk_dr();
  } else if (received[0] == 40) {
    bluetooth_dr();
    walk_dr();
  } else if (received[0] == 41) {
    bluetooth_dr();
    kakao_dr();
    walk_dr();
  } else if (received[0] == 42) {
    bluetooth_dr();
    call_dr();
    walk_dr();
  } else if (received[0] == 43) {
    bluetooth_dr();
    call_dr();
    kakao_dr();
    walk_dr();
  } else if (received[0] == 44) {
    bluetooth_dr();
    message_dr();
    walk_dr();
  } else if (received[0] == 45) {
    bluetooth_dr();
    message_dr();
    kakao_dr();
    walk_dr();
  } else if (received[0] == 46) {
    bluetooth_dr();
    message_dr();
    call_dr();
    walk_dr();
  } else if (received[0] == 47) {
    bluetooth_dr();
    message_dr();
    call_dr();
    kakao_dr();
    walk_dr();
  } else if (received[0] == 48) {
    running_dr();
  } else if (received[0] == 49) {
    kakao_dr();
    running_dr();
  } else if (received[0] == 50) {
    call_dr();
    running_dr();
  } else if (received[0] == 51) {
    call_dr();
    kakao_dr();
    running_dr();
  } else if (received[0] == 52) {
    message_dr();
    running_dr();
  } else if (received[0] == 53) {
    message_dr();
    kakao_dr();
    running_dr();
  } else if (received[0] == 54) {
    message_dr();
    call_dr();
    running_dr();
  } else if (received[0] == 55) {
    message_dr();
    call_dr();
    kakao_dr();
    running_dr();
  } else if (received[0] == 56) {
    bluetooth_dr();
    running_dr();
  } else if (received[0] == 57) {
    bluetooth_dr();
    kakao_dr();
    running_dr();
  } else if (received[0] == 58) {
    bluetooth_dr();
    call_dr();
    running_dr();
  } else if (received[0] == 59) {
    bluetooth_dr();
    call_dr();
    kakao_dr();
    running_dr();
  } else if (received[0] == 60) {
    bluetooth_dr();
    message_dr();
    running_dr();
  } else if (received[0] == 61) {
    bluetooth_dr();
    message_dr();
    kakao_dr();
    running_dr();
  } else if (received[0] == 62) {
    bluetooth_dr();
    message_dr();
    call_dr();
    running_dr();
  } else if (received[0] == 63) {
    bluetooth_dr();
    message_dr();
    call_dr();
    kakao_dr();
    running_dr();
  } else if (received[0] == 64) {
    dumbbell_dr();
  } else if (received[0] == 65) {
    kakao_dr();
    dumbbell_dr();
  } else if (received[0] == 66) {
    call_dr();
    dumbbell_dr();
  } else if (received[0] == 67) {
    call_dr();
    kakao_dr();
    dumbbell_dr();
  } else if (received[0] == 68) {
    message_dr();
    dumbbell_dr();
  } else if (received[0] == 69) {
    message_dr();
    kakao_dr();
    dumbbell_dr();
  } else if (received[0] == 70) {
    message_dr();
    call_dr();
    dumbbell_dr();
  } else if (received[0] == 71) {
    message_dr();
    call_dr();
    kakao_dr();
    dumbbell_dr();
  } else if (received[0] == 72) {
    bluetooth_dr();
    dumbbell_dr();
  } else if (received[0] == 73) {
    bluetooth_dr();
    kakao_dr();
    dumbbell_dr();
  } else if (received[0] == 74) {
    bluetooth_dr();
    call_dr();
    dumbbell_dr();
  } else if (received[0] == 75) {
    bluetooth_dr();
    call_dr();
    kakao_dr();
    dumbbell_dr();
  } else if (received[0] == 76) {
    bluetooth_dr();
    message_dr();
    dumbbell_dr();
  } else if (received[0] == 77) {
    bluetooth_dr();
    message_dr();
    kakao_dr();
    dumbbell_dr();
  } else if (received[0] == 78) {
    bluetooth_dr();
    message_dr();
    call_dr();
    dumbbell_dr();
  } else if (received[0] == 79) {
    bluetooth_dr();
    message_dr();
    call_dr();
    kakao_dr();
    dumbbell_dr();
  } else if (received[0] == 80) {
    barbell_dr();
  } else if (received[0] == 81) {
    kakao_dr();
    barbell_dr();
  } else if (received[0] == 82) {
    call_dr();
    barbell_dr();
  } else if (received[0] == 83) {
    call_dr();
    kakao_dr();
    barbell_dr();
  } else if (received[0] == 84) {
    message_dr();
    barbell_dr();
  } else if (received[0] == 85) {
    message_dr();
    kakao_dr();
    barbell_dr();
  } else if (received[0] == 86) {
    message_dr();
    call_dr();
    barbell_dr();
  } else if (received[0] == 87) {
    message_dr();
    call_dr();
    kakao_dr();
    barbell_dr();
  } else if (received[0] == 88) {
    bluetooth_dr();
    barbell_dr();
  } else if (received[0] == 89) {
    bluetooth_dr();
    kakao_dr();
    barbell_dr();
  } else if (received[0] == 90) {
    bluetooth_dr();
    call_dr();
    barbell_dr();
  } else if (received[0] == 91) {
    bluetooth_dr();
    call_dr();
    kakao_dr();
    barbell_dr();
  } else if (received[0] == 92) {
    bluetooth_dr();
    message_dr();
    barbell_dr();
  } else if (received[0] == 93) {
    bluetooth_dr();
    message_dr();
    kakao_dr();
    barbell_dr();
  } else if (received[0] == 94) {
    bluetooth_dr();
    message_dr();
    call_dr();
    barbell_dr();
  } else if (received[0] == 95) {
    bluetooth_dr();
    message_dr();
    call_dr();
    kakao_dr();
    barbell_dr();
  } else if (received[0] == 96) {
    jumprope_dr();
  } else if (received[0] == 97) {
    kakao_dr();
    jumprope_dr();
  } else if (received[0] == 98) {
    call_dr();
    jumprope_dr();
  } else if (received[0] == 99) {
    call_dr();
    kakao_dr();
    jumprope_dr();
  } else if (received[0] == 100) {
    message_dr();
    jumprope_dr();
  } else if (received[0] == 101) {
    message_dr();
    kakao_dr();
    jumprope_dr();
  } else if (received[0] == 102) {
    message_dr();
    call_dr();
    jumprope_dr();
  } else if (received[0] == 103) {
    message_dr();
    call_dr();
    kakao_dr();
    jumprope_dr();
  } else if (received[0] == 104) {
    bluetooth_dr();
    jumprope_dr();
  } else if (received[0] == 105) {
    bluetooth_dr();
    kakao_dr();
    jumprope_dr();
  } else if (received[0] == 106) {
    bluetooth_dr();
    call_dr();
    jumprope_dr();
  } else if (received[0] == 107) {
    bluetooth_dr();
    call_dr();
    kakao_dr();
    jumprope_dr();
  } else if (received[0] == 108) {
    bluetooth_dr();
    message_dr();
    jumprope_dr();
  } else if (received[0] == 109) {
    bluetooth_dr();
    message_dr();
    kakao_dr();
    jumprope_dr();
  } else if (received[0] == 110) {
    bluetooth_dr();
    message_dr();
    call_dr();
    jumprope_dr();
  } else if (received[0] == 111) {
    bluetooth_dr();
    message_dr();
    call_dr();
    kakao_dr();
    jumprope_dr();
  } else if (received[0] == 112) {
    heartbeat_dr();
  } else if (received[0] == 113) {
    kakao_dr();
    heartbeat_dr();
  } else if (received[0] == 114) {
    call_dr();
    heartbeat_dr();
  } else if (received[0] == 115) {
    call_dr();
    kakao_dr();
    heartbeat_dr();
  } else if (received[0] == 116) {
    message_dr();
    heartbeat_dr();
  } else if (received[0] == 117) {
    message_dr();
    kakao_dr();
    heartbeat_dr();
  } else if (received[0] == 118) {
    message_dr();
    call_dr();
    heartbeat_dr();
  } else if (received[0] == 119) {
    message_dr();
    call_dr();
    kakao_dr();
    heartbeat_dr();
  } else if (received[0] == 120) {
    bluetooth_dr();
    heartbeat_dr();
  } else if (received[0] == 121) {
    bluetooth_dr();
    kakao_dr();
    heartbeat_dr();
  } else if (received[0] == 122) {
    bluetooth_dr();
    call_dr();
    heartbeat_dr();
  } else if (received[0] == 123) {
    bluetooth_dr();
    call_dr();
    kakao_dr();
    heartbeat_dr();
  } else if (received[0] == 124) {
    bluetooth_dr();
    message_dr();
    heartbeat_dr();
  } else if (received[0] == 125) {
    bluetooth_dr();
    message_dr();
    kakao_dr();
    heartbeat_dr();
  } else if (received[0] == 126) {
    bluetooth_dr();
    message_dr();
    call_dr();
    heartbeat_dr();
  } else if (received[0] == 127) {
    bluetooth_dr();
    message_dr();
    call_dr();
    kakao_dr();
    heartbeat_dr();
  } else if (received[0] == 128) {
    temperature_dr();
  } else if (received[0] == 129) {
    kakao_dr();
    temperature_dr();
  } else if (received[0] == 130) {
    call_dr();
    temperature_dr();
  } else if (received[0] == 131) {
    call_dr();
    kakao_dr();
    temperature_dr();
  } else if (received[0] == 132) {
    message_dr();
    temperature_dr();
  } else if (received[0] == 133) {
    message_dr();
    kakao_dr();
    temperature_dr();
  } else if (received[0] == 134) {
    message_dr();
    call_dr();
    temperature_dr();
  } else if (received[0] == 135) {
    message_dr();
    call_dr();
    kakao_dr();
    temperature_dr();
  } else if (received[0] == 136) {
    bluetooth_dr();
    temperature_dr();
  } else if (received[0] == 137) {
    bluetooth_dr();
    kakao_dr();
    temperature_dr();
  } else if (received[0] == 138) {
    bluetooth_dr();
    call_dr();
    temperature_dr();
  } else if (received[0] == 139) {
    bluetooth_dr();
    call_dr();
    kakao_dr();
    temperature_dr();
  } else if (received[0] == 140) {
    bluetooth_dr();
    message_dr();
    temperature_dr();
  } else if (received[0] == 141) {
    bluetooth_dr();
    message_dr();
    kakao_dr();
    temperature_dr();
  } else if (received[0] == 142) {
    bluetooth_dr();
    message_dr();
    call_dr();
    temperature_dr();
  } else if (received[0] == 143) {
    bluetooth_dr();
    message_dr();
    call_dr();
    kakao_dr();
    temperature_dr();
  } else if (received[0] == 144) {
    sleep_dr();
  } else if (received[0] == 145) {
    kakao_dr();
    sleep_dr();
  } else if (received[0] == 146) {
    call_dr();
    sleep_dr();
  } else if (received[0] == 147) {
    call_dr();
    kakao_dr();
    sleep_dr();
  } else if (received[0] == 148) {
    message_dr();
    sleep_dr();
  } else if (received[0] == 149) {
    message_dr();
    kakao_dr();
    sleep_dr();
  } else if (received[0] == 150) {
    message_dr();
    call_dr();
    sleep_dr();
  } else if (received[0] == 151) {
    message_dr();
    call_dr();
    kakao_dr();
    sleep_dr();
  } else if (received[0] == 152) {
    bluetooth_dr();
    sleep_dr();
  } else if (received[0] == 153) {
    bluetooth_dr();
    kakao_dr();
    sleep_dr();
  } else if (received[0] == 154) {
    bluetooth_dr();
    call_dr();
    sleep_dr();
  } else if (received[0] == 155) {
    bluetooth_dr();
    call_dr();
    kakao_dr();
    sleep_dr();
  } else if (received[0] == 156) {
    bluetooth_dr();
    message_dr();
    sleep_dr();
  } else if (received[0] == 157) {
    bluetooth_dr();
    message_dr();
    kakao_dr();
    sleep_dr();
  } else if (received[0] == 158) {
    bluetooth_dr();
    message_dr();
    call_dr();
    sleep_dr();
  } else if (received[0] == 159) {
    bluetooth_dr();
    message_dr();
    call_dr();
    kakao_dr();
    sleep_dr();
  } else if (received[0] == 240) {
    retime_dr();
  } else if (received[0] == 241) {
    kakao_dr();
    retime_dr();
  } else if (received[0] == 242) {
    call_dr();
    retime_dr();
  } else if (received[0] == 243) {
    call_dr();
    kakao_dr();
    retime_dr();
  } else if (received[0] == 244) {
    message_dr();
    retime_dr();
  } else if (received[0] == 245) {
    message_dr();
    kakao_dr();
    retime_dr();
  } else if (received[0] == 246) {
    message_dr();
    call_dr();
    retime_dr();
  } else if (received[0] == 247) {
    message_dr();
    call_dr();
    kakao_dr();
    retime_dr();
  } else if (received[0] == 248) {
    bluetooth_dr();
    retime_dr();
  } else if (received[0] == 249) {
    bluetooth_dr();
    kakao_dr();
    retime_dr();
  } else if (received[0] == 250) {
    bluetooth_dr();
    call_dr();
    retime_dr();
  } else if (received[0] == 251) {
    bluetooth_dr();
    call_dr();
    kakao_dr();
    retime_dr();
  } else if (received[0] == 252) {
    bluetooth_dr();
    message_dr();
    retime_dr();
  } else if (received[0] == 253) {
    bluetooth_dr();
    message_dr();
    kakao_dr();
    retime_dr();
  } else if (received[0] == 254) {
    bluetooth_dr();
    message_dr();
    call_dr();
    retime_dr();
  } else if (received[0] == 255) {
    bluetooth_dr();
    message_dr();
    call_dr();
    kakao_dr();
    retime_dr();
  }
  sd = received[0];
}

//piezo buzzer(beep)

int note[] = {3520, 3520};
void beep (void) {
  //비프음
  int beepCount = sizeof(note) / sizeof(int);
  for (int bc = 0; bc < beepCount; bc++) {
    tone(speakerpin, note[bc], 50);
    delay(100);
  }
}

int time_save() {
  //if ((downum>0) && (downum<8)){
  rtc.halt(false);
  rtc.writeProtect(false);
  rtc.setDOW(downum);        // Set Day-of-Week to FRIDAY
  rtc.setTime(shour, smin, ssec);     // Set the time to 12:00:00 (24hr format)
  rtc.setDate(syear, smon, sdate);   // year, month, date
  // }
}


void setup(void) {////////////////////////////////////////////////////////////
  //// Set SPI
  SPI.begin(); // SPI 통신 초기화
  digitalWrite(SS, HIGH); // 슬레이브가 선택되지 않은 상태로 유지
  SPI.setClockDivider(SPI_CLOCK_DIV16);
  Serial.begin(115200); //수신 문자열 출력을 위한 직렬 통신 초기화

  //openning beep
  beep();

  //sdow : 요일 처리
  vidow = rtc.getDOWStr();
  in_vidow();

  // Set clock
  //  if (output == "2018.01.01") {
  //    rtc.halt(false);
  //    rtc.writeProtect(false);
  //    //rtc.writeProtect(true);
  //
  //    rtc.setDOW(MONDAY);        // Set Day-of-Week to FRIDAY
  //    rtc.setTime(18, 14, 0);     // Set the time to 12:00:00 (24hr format)
  //    rtc.setDate(2018, 6, 25);   // year, month, date
  //  }

}////////////////////////////////////////////////////////////

long readVcc() {
  // Read 1.1V reference against AVcc
  // set the reference to Vcc and the measurement to the internal 1.1V reference
#if defined(__AVR_ATmega32U4__) || defined(__AVR_ATmega1280__) || defined(__AVR_ATmega2560__)
  ADMUX = _BV(REFS0) | _BV(MUX4) | _BV(MUX3) | _BV(MUX2) | _BV(MUX1);
#elif defined (__AVR_ATtiny24__) || defined(__AVR_ATtiny44__) || defined(__AVR_ATtiny84__)
  ADMUX = _BV(MUX5) | _BV(MUX0);
#elif defined (__AVR_ATtiny25__) || defined(__AVR_ATtiny45__) || defined(__AVR_ATtiny85__)
  ADMUX = _BV(MUX3) | _BV(MUX2);
#else
  ADMUX = _BV(REFS0) | _BV(MUX3) | _BV(MUX2) | _BV(MUX1);
#endif

  delay(2); // Wait for Vref to settle
  ADCSRA |= _BV(ADSC); // Start conversion
  while (bit_is_set(ADCSRA, ADSC)); // measuring

  uint8_t low  = ADCL; // must read ADCL first - it then locks ADCH
  uint8_t high = ADCH; // unlocks both

  long vcc_result = (high << 8) | low;

  vcc_result = 1125300L / vcc_result; // Calculate Vcc (in mV); 1125300 = 1.1*1023*1000
  return vcc_result; // Vcc in millivolts
}



void loop(void) {////////////////////////////////////////////////////////////
  //millis
  current_time = millis();

  //// picture loop
  u8g.firstPage();
  do {
    if (current_time < 3000) {
      opening_logo();

    } else {
      //openning display
      initial_display_dr();
      mini_clock_dr();
      battery_dr();

    }

    a++; // logo 위한 시간 증가
    if (downum == 0 ) {
      vidow == "Sunday";
    } else if (downum == 1 ) {
      vidow == "Monday";
    } else if (downum == 2 ) {
      vidow == "Tuesday";
    } else if (downum == 3 ) {
      vidow == "Wednesday";
    } else if (downum == 4 ) {
      vidow == "Thursday";
    } else if (downum == 5 ) {
      vidow == "Friday";
    } else if (downum == 6 ) {
      vidow == "Saturday";
    }

  } while ( u8g.nextPage() );

  // SPI 통신
  count = count + 1 ;
  if (count == 1) {
    digitalWrite(SS, LOW); // 슬레이브 선택
    received[0] = SPI.transfer(0);
    digitalWrite(SS, HIGH);
  }
  if (count == 2) {
    digitalWrite(SS, LOW); // 슬레이브 선택
    received[1] = SPI.transfer(1);
    digitalWrite(SS, HIGH);
  }
  if (count == 3) {
    digitalWrite(SS, LOW); // 슬레이브 선택
    received[2] = SPI.transfer(2);
    digitalWrite(SS, HIGH);
  }
  if (count == 4) {
    digitalWrite(SS, LOW); // 슬레이브 선택
    received[3] = SPI.transfer(3);
    digitalWrite(SS, HIGH);
  }
  if (count == 5) {
    digitalWrite(SS, LOW); // 슬레이브 선택
    received[4] = SPI.transfer(4);
    digitalWrite(SS, HIGH);
  }
  if (count == 6) {
    digitalWrite(SS, LOW); // 슬레이브 선택
    received[5] = SPI.transfer(5);
    digitalWrite(SS, HIGH);
  }
  if (count == 7) {
    digitalWrite(SS, LOW); // 슬레이브 선택
    received[6] = SPI.transfer(6);
    digitalWrite(SS, HIGH);
  }
  if (count == 8) {
    digitalWrite(SS, LOW); // 슬레이브 선택
    received[7] = SPI.transfer(7);
    digitalWrite(SS, HIGH);
  }
  if (count == 9) {
    digitalWrite(SS, LOW); // 슬레이브 선택
    bc = SPI.transfer(16);
    digitalWrite(SS, HIGH);
    delay(10);
  }
  if (received[0] != '0') {
    Serial.print("[0]: ");
    Serial.println(received[0]);
    Serial.print("[1]: ");
    Serial.println(received[1]);
    Serial.print("[2]: ");
    Serial.println(received[2]);
    Serial.print("[3]: ");
    Serial.println(received[3]);
    Serial.print("[4]: ");
    Serial.println(received[4]);
    Serial.print("[5]: ");
    Serial.println(received[5]);
    Serial.print("[6]: ");
    Serial.println(received[6]);
    Serial.print("[7]: ");
    Serial.println(received[7]);
    Serial.print("vidow:");
    Serial.println(vidow);
    Serial.print("downum:");
    Serial.println(downum);

  }
  count == 10 ? count = 0 : count;
  digitalWrite(SS, HIGH);

  // SPI 받은 데이터 처리
  total = (received[1] * 100000 + received[2] * 10000 + received[3] * 1000 + received[4] * 100 + received[5] * 10 + received[6]);
  itoa( total, total_dr, radix);
  Serial.println(total_dr);


  // SPI slave에 8,9,10 신호 보낼 때, 8:배터리부족, 9:sleep모드 10:00시00분
  if (vcc_result < 1200) {
    digitalWrite(SS, LOW);
    SPI.transfer(8);
    digitalWrite(SS, HIGH);
  }
  if (count == 18000) {
    digitalWrite(SS, LOW);
    SPI.transfer(9);
    digitalWrite(SS, HIGH);
  }
  String timesa = rtc.getTimeStr();
  String times = "23:59:59";
  if ( timesa == times ) {
    digitalWrite(SS, LOW);
    SPI.transfer(10);
    digitalWrite(SS, HIGH);
  }

  // 배터리 전압체크
  vcc_result = readVcc();
  Serial.print("vcc : ");
  Serial.println(vcc_result);
  Serial.print("time : ");
  Serial.println(rtc.getTimeStr());
  Serial.print("date : ");
  Serial.println(rtc.getDateStr());

  // rebuild the picture after some delay
  delay(10);
}////////////////////////////////////////////////////////////

