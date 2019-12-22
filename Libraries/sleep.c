/*
 * Cross-platform sleep function.
 * https://stackoverflow.com/a/28827188
 */

#ifndef CP_SLEEP
#define CP_SLEEP

// Prototype
void cp_sleep(int);

#ifdef WIN32

#include <windows.h>

void cp_sleep(int milliseconds) {
  Sleep(milliseconds);
}

#elif _POSIX_C_SOURCE >= 199309L

#include <time.h>

void cp_sleep(int milliseconds) {
  struct timespec ts;

  ts.tv_sec = milliseconds / 1000;
  ts.tv_nsec = (milliseconds % 1000) * 1000000;
  nanosleep(&ts, NULL);
}

#else  /* ifdef WIN32 */

#include <unistd.h>

void cp_sleep(int milliseconds) {
  usleep(milliseconds * 1000);
}

#endif /* ifdef WIN32 */

#endif /* ifndef cp_sleep */
