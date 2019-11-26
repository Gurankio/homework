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
#include <stdbool.h>

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
void risolvi(float, float, float, float, float, float);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  float a1, b1, c1, a2, b2, c2;
  chiedi("Inserisci il coefficente di x: (Prima Equazione) ", "%f", &a1);
  chiedi("Inserisci il coefficente di y: (Prima Equazione) ", "%f", &b1);
  chiedi("Inserisci il termine noto: (Prima Equazione) ", "%f", &c1);
  chiedi("Inserisci il coefficente di x: (Seconda Equazione) ", "%f", &a2);
  chiedi("Inserisci il coefficente di y: (Seconda Equazione) ", "%f", &b2);
  chiedi("Inserisci il termine noto: (Seconda Equazione) ", "%f", &c2);
  risolvi(a1, b1, c1, a2, b2, c2);

  // getchar();
  // system("pause");
  return 0;
}

void risolvi(float a1, float b1, float c1, float a2, float b2, float c2) {
  float d = a1 * b2 - a2 * b1;
  float rx = c1 * b2 - c2 * b1;
  float ry = a1 * c2 - a2 * c1;

  if (d != 0) {
    printf("X = %.2f\n", rx / d);
    printf("Y = %.2f\n", ry / d);
  } else if (rx == 0 || ry == 0) printf("Il sistema è indeterminato.\n");
  else printf("Il sistema è impossibile.\n");
}
