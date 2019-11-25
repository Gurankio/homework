/*
 * Jacopo Del Granchio
 * #045 26.11.2019
 *
 * Stampa i numeri dispari fino a 99.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>

// Prototipi
void chiedi(char *, char *, ...);
int dispari(int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  for (int i = 0; i < 100; ++i)
    if (dispari(i)) printf("%d\t", i);

  printf("\n");

  // getchar();
  // system("pause");
  return 0;
}

void chiedi(char *msg, char *format, ...) {
  printf(msg);
  va_list list;
  va_start(list, format);
  vscanf(format, list);
  va_end(list);
}

int dispari(int n) {
  return n % 2;
}
