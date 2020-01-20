/*
 * Jacopo Del Granchio
 * #084 21.01.20
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

#define RIGHE   10
#define COLONNE 3

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
void carica(float[RIGHE][COLONNE], int);
void scarica(float[RIGHE][COLONNE]);
void calcola(float[RIGHE][COLONNE]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  float squadre[RIGHE][COLONNE];

  printf("1 Colonna - Inter\n");
  carica(squadre, 0);
  printf("2 Colonna - Barcellona\n");
  carica(squadre, 1);

  calcola(squadre);

  printf("\nOutput:\n");
  scarica(squadre);
  // getchar();
  // system("pause");
  return 0;
}

void carica(float squadre[RIGHE][COLONNE], int colonna) {
  for (int i = 0; i < RIGHE; i++) {
    //
    chiedi("Inserire un numero: ", "%f", &squadre[i][colonna]);
    //
  }
}

void scarica(float squadre[RIGHE][COLONNE]) {
  for (int i = 0; i < RIGHE; i++) {
    for (int j = 0; j < COLONNE; j++)
      printf("%.2f\t", squadre[i][j]);

    printf("\n");
  }
}

void calcola(float squadre[RIGHE][COLONNE]) {
  for (int i = 0; i < RIGHE; i++)
    squadre[i][2] = squadre[i][0] + squadre[i][1];
}
