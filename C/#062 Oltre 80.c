/*
 * Jacopo Del Granchio
 * #062 11.12.2019
 *
 * Calcola la tariffa telefonica 'oltre80'.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int timeInSeconds(int, int, int);
float cost(int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int h, m, s;
  chiedi("Inserisci il tempo della telefonata: ", " %d %d %d", &h, &m, &s);
  float tot = cost(timeInSeconds(h, m, s));
  printf("Il costo totale della chiamata è %.2f\n", tot);

  // getchar();
  // system("pause");
  return 0;
}

int timeInSeconds(int h, int m, int s) {
  m += h * 60;
  s += m * 60;
  return s;
}

float cost(int time) {
  const float SCATTO = 0.10;
  const float SOTTO_80 = 0.15;
  const float OLTRE_80 = 0.09;
  float tot;

  tot += SCATTO;
  tot += SOTTO_80 * (time < 80 ? time : 80);
  tot += OLTRE_80 * (time > 80 ? time - 80 : 0);

  return tot;
}
