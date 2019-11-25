/*
 * Jacopo Del Granchio
 * #049 27.11.2019
 *
 * Data una sequenza terminata da uno 0 calcola il numero di vocali.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>

// Prototipi
void chiedi(char *, char *, ...);
int vocale(char);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int r = 0;
  char t = '0';
  do {
    if (vocale(t)) r++;

    chiedi("\rInserisci un carattere: ", " %c", &t);
  } while (t != '0');

  printf("Le vocali sono %d.\n", r);

  // getchar();
  // system("pause"
  return 0;
}

void chiedi(char *msg, char *format, ...) {
  printf(msg);
  va_list list;
  va_start(list, format);
  vscanf(format, list);
  va_end(list);
}

int vocale(char c) {
  return c == 'a' || c == 'A' ||
         c == 'e' || c == 'E' ||
         c == 'i' || c == 'I' ||
         c == 'o' || c == 'O' ||
         c == 'u' || c == 'U';
}
