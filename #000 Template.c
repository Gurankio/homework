/*
 * Jacopo Del Granchio
 * #000 GG.MM.YYYY
 *
 * Lorem ipsum dolor sit amet, consectetur adipisicing elit,
 * sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>

#define input(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

int main() {
  setlocale(LC_ALL, "");

  // getchar();
  // system("pause");
  return 0;
}
