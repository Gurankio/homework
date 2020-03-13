#include <termios.h>
#include <stdio.h>

static struct termios old, current;

/* Initialize new terminal i/o settings */
void initTermios(int echo) {
  tcgetattr(0, &old); /* grab old terminal i/o settings */
  current = old; /* make new settings same as old settings */
  current.c_lflag &= ~ICANON; /* disable buffered i/o */

  if (echo) current.c_lflag |= ECHO;  /* set echo mode */
  else current.c_lflag &= ~ECHO;      /* set no echo mode */

  tcsetattr(0, TCSANOW, &current); /* use these new terminal i/o settings now */
}

/* Restore old terminal i/o settings */
void resetTermios(void) {
  tcsetattr(0, TCSANOW, &old);
}

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>  //Header file for sleep(). man 3 sleep for details.
#include <pthread.h>
#include <time.h>

int last = 0;

void * myThreadFun(void *vargp) {
  initTermios(0);
  char ch;

  int esc = 0;
  int command = 0;

  while ((ch = getchar()) != 0) {
    // printf("%02d\n", ch);

    if (command) {
      switch (ch) {
        case 65:
          printf("Input UP\n");
          last = 1;
          break;

        case 66:
          printf("Input DOWN\n");
          last = 2;
          break;

        case 67:
          printf("Input RIGHT\n");
          last = 3;
          break;

        case 68:
          printf("Input LEFT\n");
          last = 4;
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

int main() {
  pthread_t thread_id;

  printf("Before Thread\n");
  pthread_create(&thread_id, NULL, myThreadFun, NULL);

  while (1) {
    sleep(1);
    printf("%ld\t", time(NULL));
    switch (last) {
      case 1:
        printf("Main UP\n");
        break;

      case 2:
        printf("Main DOWN\n");
        break;

      case 3:
        printf("Main RIGHT\n");
        break;

      case 4:
        printf("Main LEFT\n");
        break;
    }
  }

  pthread_join(thread_id, NULL);
  printf("After Thread\n");
  exit(0);
}
