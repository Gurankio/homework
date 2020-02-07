/*
 * Jacopo Del Granchio
 * #092 GG.MM.YYYY
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

#include "libraries/sleep.c"
#include "libraries/vts.c"
#include "libraries/atr.c"


// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
void griglia(atr_layer *layer);
void aiuti(atr_layer *layer);
int vittoria(int m[6][7]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");
  vts_activateCommands();

  int m[6][7];

  for (int i = 0; i < 6; i++)
    for (int j = 0; j < 7; j++)
      m[i][j] = -1;

  // 0 -> Griglia
  // 1 -> Aiuti
  // 2 -> Giocatore 1
  // 3 -> Giocatore 2
  const int WIDTH = 29, HEIGHT = 19;
  // vts_resize(WIDTH * 2, HEIGHT + 2);
  atr_layer layers[4];

  atr_layerStart(&layers[0], WIDTH, HEIGHT);
  atr_layerTypeSet(&layers[0], ATR_LINES);

  atr_layerStart(&layers[1], WIDTH, HEIGHT);

  atr_layerStart(&layers[2], WIDTH, HEIGHT);
  atr_layerTypeSet(&layers[2], ATR_RED | ATR_BOLD);

  atr_layerStart(&layers[3], WIDTH, HEIGHT);
  atr_layerTypeSet(&layers[3], ATR_BLUE | ATR_BOLD);

  griglia(&layers[0]);
  aiuti(&layers[1]);

  int player = 0;

  do {
    vts_clear();
    atr_printLayers(layers, 4);

    // INPUT
    int s;

    do {
      printf("Fai la tua mossa giocatore %s: ", player ? "blu" : "rosso");
      scanf("%d", &s);
      s--;

      if (s < 0 || s > 6) printf("Input non valido.\n");
      else if (m[0][s] != -1) printf("Non c'e' spazio.\n");
    } while (s < 0 || s > 6 || m[0][s] != -1);


    // ANIMAZIONE e COLLISIONE
    int r = 0;

    do {
      //
      if (m[r + 1][s] == -1) r++;
      else break;

      //
    } while (r < 6);

    m[r][s] = player;

    for (int k = 0; k <= r; k++) {
      if (k != 0)
        for (int i = 0; i < 2; i++)
          for (int j = 0; j < 3; j++)
            atr_layerSet(&layers[2 + player], (1 + j) + (s * 4), (1 + i) + (k - 1) * 3, -1);

      for (int i = 0; i < 2; i++)
        for (int j = 0; j < 3; j++)
          atr_layerSet(&layers[2 + player], (1 + j) + (s * 4), (1 + i) + (k) * 3, 'X');

      vts_clear();
      atr_printLayers(layers, 4);
      cp_sleep(250);
    }

    // CONTROLLA VITTORIA.
    /*int vincente = vittoria(m);

       if (vincente != -1) {
       printf("%d", vincente);
       break;
       }
     */

    player = !player;
  } while (1);


  atr_layerEnd(&layers[0]);
  atr_layerEnd(&layers[1]);
  atr_layerEnd(&layers[2]);
  atr_layerEnd(&layers[3]);
  // getchar();
  // system("pause");
  return 0;
}

int vittoria(int m[6][7]) {
  int out = -1;

  for (int i = 0; i < 6; i++) {
    for (int k = 0; k < 3; k++) {
      int somma = 0;

      for (int j = k; j < 4; j++)
        somma += m[i][j];

      if (somma == 0) out = 0;
      else if (somma == 1) out = 1;
    }
  }

  return out;
}

void griglia(atr_layer *layer) {
  for (int i = 0; i <= 7; i++)
    for (int j = 0; j < layer->height; j++)
      atr_layerSet(layer, i * 4, j, 'x');

  for (int i = 0; i <= 5; i++)
    for (int j = 0; j < layer->width; j++)
      atr_layerSet(layer, j, 3 + i * 3, 'q');
}

void aiuti(atr_layer *layer) {
  for (int i = 0; i < 7; i++)
    atr_layerSetFormat(layer, 2 + i * 4, 0, "%d", i + 1);
}
