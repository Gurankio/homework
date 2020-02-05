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

const int INIZIALE = 20;
const int MASSIMO_TRANSAZIONE = 5;

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int menu();
void prelievo(int [3]);
void ricorsioneFacile(int x, int a, int b, int c, int out[][3], int *next);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int banconote[3] = { INIZIALE, INIZIALE, INIZIALE };
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
        printf("\n");
        int somma = 0;
        somma += banconote[0] * 50;
        somma += banconote[1] * 20;
        somma += banconote[2] * 10;
        printf("Il totale e' %d\n", somma);
        break;

      case 0:
        printf("Arrivederci.\n");
        break;
    }
  } while (scelta != 0);
  return 0;
}

int menu() {
  printf("\n");
  printf("1: Prelievo\n");
  printf("0: Esci\n");

  int n;
  chiedi("Scelta: ", "%d", &n);
  return n;
}

int A, B, C;

void prelievo(int banconote[3]) {
  const int MIN = 50, MAX = 400;

  int q;

  chiedi("Inserire la quatita': ", "%d", &q);

  if (q < MIN || q > MAX || q % 10 != 0) {
    printf("Quantita' non valida.\n");
    return;
  }

  int out[100][3], count = 0;
  A = banconote[0] > MASSIMO_TRANSAZIONE ? MASSIMO_TRANSAZIONE : banconote[0];
  B = banconote[1] > MASSIMO_TRANSAZIONE ? MASSIMO_TRANSAZIONE : banconote[1];
  C = banconote[2] > MASSIMO_TRANSAZIONE ? MASSIMO_TRANSAZIONE : banconote[2];
  ricorsioneFacile(q, A, B, C, out, &count);

  if (count == 0) {
    printf("Non e' possibile prelevare questa quantita'.\n");
    return;
  }

  printf("\n");
  // printf("    50 / 20 / 10\n");

  for (int i = 0; i < count; i++)
    printf("%03d) %d / %d / %d\n", i + 1, out[i][0], out[i][1], out[i][2]);

  chiedi("Scelta della combinazione: ", "%d", &q);

  if (q >= 1 && q <= count) {
    banconote[0] -= out[q - 1][0];
    banconote[1] -= out[q - 1][1];
    banconote[2] -= out[q - 1][2];

    printf("Grazie per la transazione.\n");
  } else printf("Scelta non valida.\n");
}

void ricorsioneFacile(int x, int a, int b, int c, int out[][3], int *next) {
  if (x == 0) {
    int esiste = 0;

    for (int i = 0; i < *next; i++)
      if (out[i][0] == (A - a) && out[i][1] == (B - b) && out[i][2] == (C - c)) esiste = 1;

    if (!esiste) {
      out[*next][0] = A - a;
      out[*next][1] = B - b;
      out[*next][2] = C - c;
      (*next)++;
    }
  }

  if (x >= 50 && a > 0) ricorsioneFacile(x - 50, a - 1, b, c, out, next);

  if (x >= 20 && b > 0) ricorsioneFacile(x - 20, a, b - 1, c, out, next);

  if (x >= 10 && c > 0) ricorsioneFacile(x - 10, a, b, c - 1, out, next);
}
