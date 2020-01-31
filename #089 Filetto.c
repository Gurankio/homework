/*
 * Jacopo Del Granchio
 * #089 29.01.2020
 *
 * Gioco del filetto.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>

#include "libraries/atr.c"

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int checkWin(int matrix[3][3]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");
  vts_activateCommands();

  int matrix[3][3];

  for (int i = 0; i < 3; i++)
    for (int j = 0; j < 3; j++)
      matrix[i][j] = 0;

  // 0 -> X/O
  // 1 -> Consigli all'input
  // 2 -> Griglia
  atr_layer layers[3];
  int width = 11, height = 11;

  atr_layerStart(&layers[0], width, height);
  atr_layerStart(&layers[1], width, height);
  atr_layerStart(&layers[2], width, height);

  // Inizializzo 1
  atr_layerTypeSet(&layers[1], ATR_GREEN);

  atr_layerSet(&layers[1], 1, 1, '1');
  atr_layerSet(&layers[1], 5, 1, '2');
  atr_layerSet(&layers[1], 9, 1, '3');
  atr_layerSet(&layers[1], 1, 5, '4');
  atr_layerSet(&layers[1], 5, 5, '5');
  atr_layerSet(&layers[1], 9, 5, '6');
  atr_layerSet(&layers[1], 1, 9, '7');
  atr_layerSet(&layers[1], 5, 9, '8');
  atr_layerSet(&layers[1], 9, 9, '9');

  // Inizializzo 2
  atr_layerTypeSet(&layers[2], ATR_LINES);

  for (int j = 0; j < 2; j++)
    for (int i = 0; i < height; i++)
      atr_layerSet(&layers[2], 4 * (j + 1) - 1, i, 'x');

  for (int j = 0; j < 2; j++)
    for (int i = 0; i < width; i++)
      atr_layerSet(&layers[2], i, 4 * (j + 1) - 1, i == 3 || i == 7 ? 'n' : 'q');

  // Game Loop
  int state = 0;

  for (int i = 0; i < 9; i++) {
    vts_clear();
    atr_printLayers(layers, 3);

    int n, row, column;
    do {
      chiedi("\nFai la tua mossa: ", "%d", &n);
      row = (n - 1) % 3, column = (n - 1) / 3;

      if (matrix[row][column] != 0) printf("Mossa non valida\n");
    } while (matrix[row][column] != 0);


    atr_layerSet(&layers[0], row * 4 + 1, column * 4 + 1, i % 2 ? 'X' : 'O');
    matrix[row][column] = i % 2 ? 1 : -1;
    state = checkWin(matrix);

    if (state != 0) break;
  }

  vts_clear();
  atr_printLayers(layers, 3);

  switch (state) {
    case -1:
      printf("Ha vinto il giocatore O.\n");
      break;

    case 0:
      printf("Pareggio.\n");
      break;

    case 1:
      printf("Ha vinto il giocatore X.\n");
      break;
  }

  atr_layerEnd(&layers[0]);
  atr_layerEnd(&layers[1]);
  atr_layerEnd(&layers[2]);

  // getchar();
  // system("pause");
  return 0;
}

int checkWin(int matrix[3][3]) {
  // Righe.
  for (int i = 0; i < 3; i++) {
    int sum = 0;

    for (int j = 0; j < 3; j++)
      sum += matrix[i][j];

    if (abs(sum) == 3) return sum > 0 ? 1 : -1;
  }

  // Colonne.
  for (int i = 0; i < 3; i++) {
    int sum = 0;

    for (int j = 0; j < 3; j++)
      sum += matrix[j][i];

    if (abs(sum) == 3) return sum > 0 ? 1 : -1;
  }

  // Diagonale 1
  int sum = 0;

  for (int j = 0; j < 3; j++)
    sum += matrix[j][j];

  if (abs(sum) == 3) return sum > 0 ? 1 : -1;

  // Diagonale 2
  sum = 0;

  for (int j = 0; j < 3; j++)
    sum += matrix[2 - j][j];

  if (abs(sum) == 3) return sum > 0 ? 1 : -1;

  // Tie.
  return 0;
}
