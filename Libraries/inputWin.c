#include <stdio.h>
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <conio.h>

#include "sleep.c"

int last = 0;

void * myThreadFun(void *vargp) {
  char ch;

  int command = 0;
  
  // getch / getche
  // non echo / echo
  while ((ch = getch()) != 0) {
    // printf("%02d\n", ch);

    if (command) {
      switch (ch) {
        case 72:
          printf("Input UP\n");
          last = 1;
          break;

        case 80:
          printf("Input DOWN\n");
          last = 2;
          break;

        case 77:
          printf("Input RIGHT\n");
          last = 3;
          break;

        case 75:
          printf("Input LEFT\n");
          last = 4;
          break;
      }
    }

    if (ch == -32) command = 1;
    else command = 0;
  }

  return NULL;
}

int main() {
  pthread_t thread_id;

  printf("Before Thread\n");
  pthread_create(&thread_id, NULL, myThreadFun, NULL);

  while (1) {
    cp_sleep(250);
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
  return 0;
}
