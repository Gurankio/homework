/*
 * Cross-platform precision time utility function.
 * https://gist.github.com/erichschroeter/8199129
 */

#ifndef CP_INSTANT
#define CP_INSTANT

#ifdef _WIN32

// Windows

typedef LARGE_INTEGER instant;

void updateInstant(instant *instant) {
  QueryPerformanceCounter(instant);
}

long deltaNano(instant *start, instant *end) {
	LARGE_INTEGER Frequency, elapsed;

	QueryPerformanceFrequency(&Frequency);
	elapsed.QuadPart = end->QuadPart - start->QuadPart;

	elapsed.QuadPart *= 1000000000;
	elapsed.QuadPart /= Frequency.QuadPart;

	return elapsed.QuadPart;
}

long deltaMicro(instant *start, instant *end) {
	LARGE_INTEGER Frequency, elapsed;

	QueryPerformanceFrequency(&Frequency);
	elapsed.QuadPart = end->QuadPart - start->QuadPart;

	elapsed.QuadPart *= 1000000;
	elapsed.QuadPart /= Frequency.QuadPart;

	return elapsed.QuadPart;
}

long deltaMilli(instant *start, instant *end) {
	LARGE_INTEGER Frequency, elapsed;

	QueryPerformanceFrequency(&Frequency);
	elapsed.QuadPart = end->QuadPart - start->QuadPart;

	elapsed.QuadPart *= 1000;
	elapsed.QuadPart /= Frequency.QuadPart;

	return elapsed.QuadPart;
}

#else

#include <time.h>

// Mac / Unix

typedef struct timespec instant;

void updateInstant(instant *instant) {
  clock_gettime(CLOCK_MONOTONIC, instant);
}

long delatNano(instant *start, instant *end) {
	return ((end->tv_sec * (1000000000)) + (end->tv_nsec)) - ((start->tv_sec * 1000000000) + (start->tv_nsec));
}

long deltaMicro(instant *start, instant *end) {
	return ((end->tv_sec * (1000000)) + (end->tv_nsec / 1000)) - ((start->tv_sec * 1000000) + (start->tv_nsec / 1000));
}

long deltaMilli(instant *start, instant *end) {
  return ((end->tv_sec * 1000) + (end->tv_nsec / 1000000)) - ((start->tv_sec * 1000) + (start->tv_nsec / 1000000));
}

#endif

#endif

// const int FPS = 30;
// const int TARGET = 1000 / FPS;
//
// int main() {
//   instant start, end;
//   long sleepTime;
//
//   // First Cycle Start
//   updateInstant(&start);
//
//   // Loop @ FPS
//   while (state) {
//
//     /*
//       Code here.
//     */
//
//     fflush(stdout); // Flush output
//     updateInstant(&end); // Cycle End
//     sleepTime = TARGET - deltaMilli(&start, &end); // Delta Time
//     if (sleepTime > 0) cp_sleep(sleepTime); // Wait
//     updateInstant(&start); // Next Cycle Start
//   }
//
//   return 0;
// }
