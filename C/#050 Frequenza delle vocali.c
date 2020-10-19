/*
 * Jacopo Del Granchio
 * #050 27.11.2019
 *
 * Data una sequenza terminata da uno 0 calcola la frequenza di ogni vocale.
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

  int tot = 0;
  float a = 0, e = 0, i = 0, o = 0, u = 0;
  char c = '0';
  do {
    switch (vocale(c)) {
      case 1: a++; break;

      case 2: e++; break;

      case 3: i++; break;

      case 4: o++; break;

      case 5: u++; break;
    }

    chiedi("\rInserisci un carattere: ", " %c", &c);
    tot++;
  } while (c != '0');

  if (tot != 1) tot--;

  a /= tot; a *= 100;
  e /= tot; e *= 100;
  i /= tot; i *= 100;
  o /= tot; o *= 100;
  u /= tot; u *= 100;

  printf("Le frequenza di ogni vocale sono:\n");
  printf("\tA: %.2f%%\n", a);
  printf("\tE: %.2f%%\n", e);
  printf("\tI: %.2f%%\n", i);
  printf("\tO: %.2f%%\n", o);
  printf("\tU: %.2f%%\n", u);

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

int vocale(char c) {
  if (c >= 97) c -= 32;

  if (c == 'A') return 1;
  else if (c == 'E') return 2;
  else if (c == 'I') return 3;
  else if (c == 'O') return 4;
  else if (c == 'U') return 5;
  else return 0;
}
