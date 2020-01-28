/*
 * ATR - Advanced Terminal Rendering
 */

#ifndef ATR
#define ATR

#include <stdlib.h>
#include <stdio.h>
#include <stdarg.h>
#include <string.h>

#include "vts.c"

/*
 * Type of Rendering to use.
 */
 #define ATR_DEFAULT 0
 #define ATR_LINES   1
 #define ATR_RED     11
 #define ATR_BLUE    12
 #define ATR_GREEN   13


typedef struct {
  int width;
  int height;
  char **data;
  int type;
} atr_layer;

void atr_layerStart(atr_layer *layer, int width, int height);
void atr_layerEnd(atr_layer *layer);

void atr_layerTypeSet(atr_layer *layer, int type);

void atr_layerSet(atr_layer *layer, int x, int y, char c);
void atr_layerSetBuffer(atr_layer *layer,  int x, int y, char c[]);
void atr_layerSetFormat(atr_layer *layer, int x, int y, char c[], ...);

static void typeRender(int type);
// static void dataMerge(char **data, char *output);

void printLayer(atr_layer layer);
void printLayers(atr_layer layers[], int count);

// Funzioni

void atr_layerStart(atr_layer *layer, int width, int height) {
  layer->width = width;
  layer->height = height;
  layer->data = malloc(height * sizeof(char *));

  for (int i = 0; i < height; i++) {
    layer->data[i] = malloc(width * sizeof(char));
    memset(layer->data[i], -1, width);
  }

  layer->type = 0;
}

void atr_layerEnd(atr_layer *layer) {
  free(layer->data);
}

//

void atr_layerTypeSet(atr_layer *layer, int type) {
  layer->type = type;
}

//

void atr_layerSet(atr_layer *layer, int x, int y, char c) {
  layer->data[y][x] = c;
}

void atr_layerSetBuffer(atr_layer *layer,  int x, int y, char *c) {
  if (y < 0 || y > layer->height) return;

  for (int i = 0; i < strlen(c); i++)
    atr_layerSet(layer, x + i, y, c[i]);
}

void atr_layerSetFormat(atr_layer *layer, int x, int y, char pattern[], ...) {
  char buffer[layer->width - x];
  va_list list;

  va_start(list, pattern);
  vsprintf(buffer, pattern, list);
  va_end(list);

  atr_layerSetBuffer(layer, x, y, buffer);
}

//

static void typeRender(int type) {
  vts_asciiSet();
  switch (type) {
    case ATR_DEFAULT:
      vts_foregroundDefault();
      break;

    case ATR_LINES:
      vts_lineDrawingSet();
      break;

    case ATR_RED:
      vts_foregroundBrightRed();
      break;

    case ATR_BLUE:
      vts_foregroundBrightCyan();
      break;

    case ATR_GREEN:
      vts_foregroundBrightGreen();
      break;
  }
}

void printLayer(atr_layer layer) {
  typeRender(layer.type);

  for (int i = 0; i < layer.height; i++) {
    for (int j = 0; j < layer.width; j++)
      printf("%c", layer.data[i][j] != -1 ? layer.data[i][j] : ' ');

    printf("\n");
  }
}

void printLayers(atr_layer layers[], int count) {
  if (count < 1) return;

  for (int i = 0; i < layers[0].height; i++) {
    for (int j = 0; j < layers[0].width; j++) {
      int n;

      for (n = 0; n < count; n++) {
        char value = layers[n].data[i][j];

        if (value == -1) continue;

        typeRender(layers[n].type);
        printf("%c", value);
        break;
      }

      if (n == count) {
        typeRender(ATR_DEFAULT); // Default
        printf("%c", ' ');
      }
    }

    printf("\n");
  }
}

#endif /* ifndef ATR */
