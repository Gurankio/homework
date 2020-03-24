#include <stdio.h>
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#include "sleep.c"

// Output
int lastArrow = -1;

// Static Settings
static pthread_t ID;
static int INPUT_ECHO = 0;
static int INPUT_STATE = 0;

// Prototypes
void * inputThread(void *);
void startInputThread(int echo);
void resumeInputThread();
void stopInputThread();
void killInputThread();

// Platform dependant functions
#ifdef _WIN32

#include <conio.h>

void * inputThread(void *vargp) {
  char ch;

  int command = 0;

  while (INPUT_STATE) {
    if (INPUT_STATE == -1) {
      // cp_sleep(100);
      continue;
    }

    if (INPUT_ECHO) ch = getche();
    else ch = getch();

    if (command) {
      switch (ch) {
        case 72:
          // printf("Input UP\n");
          lastArrow = 1;
          break;

        case 80:
          // printf("Input DOWN\n");
          lastArrow = 2;
          break;

        case 77:
          // printf("Input RIGHT\n");
          lastArrow = 3;
          break;

        case 75:
          // printf("Input LEFT\n");
          lastArrow = 4;
          break;
      }
    }

    if (ch == -32) command = 1;
    else command = 0;
  }

  return NULL;
}

#else

#include <termios.h>

static struct termios old, current;

void initTermios(int echo) {
  tcgetattr(0, &old);
  current = old;
  current.c_lflag &= ~ICANON;

  if (echo) current.c_lflag |= ECHO;
  else current.c_lflag &= ~ECHO;

  tcsetattr(0, TCSANOW, &current);
}

void resetTermios(void) {
  tcsetattr(0, TCSANOW, &old);
}

void * inputThread(void *vargp) {
  initTermios(INPUT_ECHO);
  char ch;

  int esc = 0;
  int command = 0;

  while (INPUT_STATE) {
    if (INPUT_STATE == -1) {
      // cp_sleep(100);
      continue;
    }

    ch = getchar();

    if (command) {
      switch (ch) {
        case 65:
          // printf("Input UP\n");
          lastArrow = 1;
          break;

        case 66:
          // printf("Input DOWN\n");
          lastArrow = 2;
          break;

        case 67:
          // printf("Input RIGHT\n");
          lastArrow = 3;
          break;

        case 68:
          // printf("Input LEFT\n");
          lastArrow = 4;
          break;
      }
    }

    if (esc && ch == 91) command = 1;
    else command = 0;

    if (ch == 27) esc = 1;
    else esc = 0;
  }

  resetTermios();
  return NULL;
}

#endif

void startInputThread(int echo) {
  pthread_create(&ID, NULL, inputThread, NULL);
  resumeInputThread();
}

void resumeInputThread() {
  INPUT_STATE = 1;
}

void stopInputThread() {
  INPUT_STATE = -1;
}

void killInputThread() {
  INPUT_STATE = 0;
  pthread_join(ID, NULL);
}
