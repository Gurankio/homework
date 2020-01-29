/*
 * Jacopo Del Granchio
 * #090 01.02.2020
 *
 * Bancomat:
 * 20 di ogni banconota (50, 20, 10) e vanno aggiornate le quatità dopo ogni prelievo
 * Controllo se può darli.
 * Max 400 = 5 da 50, 5 da 20, 5 da 10.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>

#define INITIAL 20

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int menu();

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int banconote[3] = { INITIAL, INITIAL, INITIAL };
  int scelta;

  do {
    scelta = menu();
    //
    //
    switch (scelta) {
      case 1:
        prelievo(banconote);
        break;

      case 2:
        printf("Arrivederci.\n");
        break;
    }
    //
  } while (scelta != 2);

  // getchar();
  // system("pause");
  return 0;
}

int menu() {
  printf("1: Prelievo");
  printf("2: Esci");

  int n;
  chiedi("Scelta: ", "%d", &n);
  return n;
}

void prelievo(int banconote[3]) {
  int q;

  chiedi("Inserire la quatità: ", "%d", &q);
}
