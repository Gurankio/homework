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
 #define ATR_DEFAULT 0b00000000
 #define ATR_LINES   0b00000001
 #define ATR_BOLD    0b00000010
 #define ATR_RED     0b00000100
 #define ATR_BLUE    0b00001000
 #define ATR_GREEN   0b00010000


typedef struct {
  int width;
  int height;
  char **data;
  int type;
} atr_layer;

void atr_layerStart(atr_layer *layer, int width, int height);
void atr_layerEnd(atr_layer *layer);

void atr_layerTypeSet(atr_layer *layer, int type);

char atr_layerGet(atr_layer *layer, int x, int y);
void atr_layerSet(atr_layer *layer, int x, int y, char c);

void atr_layerSetBuffer(atr_layer *layer,  int x, int y, char c[]);
void atr_layerSetFormat(atr_layer *layer, int x, int y, char c[], ...);

static void atr_typeRender(int type);
// static void dataMerge(char **data, char *output);

void atr_printLayer(atr_layer layer);
void atr_printLayers(atr_layer layers[], int count);

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

char atr_layerGet(atr_layer *layer, int x, int y) {
  return layer->data[y][x];
}

void atr_layerSet(atr_layer *layer, int x, int y, char c) {
  if (x < 0 || x > layer->width) fprintf(stderr, "atr_layerSet(): Parameter X (%d) out of bounds.\n", x);

  if (y < 0 || y > layer->height) fprintf(stderr, "atr_layerSet(): Parameter Y (%d) out of bounds.\n", y);

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

// TODO: BITWISE
static void atr_typeRender(int type) {
  vts_asciiSet();
  vts_foregroundDefault();
  vts_textDefault();

  if (type % 2) vts_lineDrawingSet();

  type /= 2;

  if (type % 2) vts_textBold();

  type /= 2;

  if (type % 2) vts_foregroundRed();

  type /= 2;

  if (type % 2) vts_foregroundCyan();

  type /= 2;

  if (type % 2) vts_foregroundGreen();

  type /= 2;
}

void atr_printLayer(atr_layer layer) {
  atr_typeRender(layer.type);

  for (int i = 0; i < layer.height; i++) {
    for (int j = 0; j < layer.width; j++)
      putchar(layer.data[i][j] != -1 ? layer.data[i][j] : ' ');

    putchar('\n');
  }

  atr_typeRender(ATR_DEFAULT); // Default
}

void atr_printLayers(atr_layer layers[], int count) {
  if (count < 1) return;

  for (int i = 0; i < layers[0].height; i++) {
    for (int j = 0; j < layers[0].width; j++) {
      int n;

      for (n = 0; n < count; n++) {
        char value = layers[n].data[i][j];

        if (value == -1) continue;

        atr_typeRender(layers[n].type);
        putchar(value);
        break;
      }

      if (n == count) {
        atr_typeRender(ATR_DEFAULT); // Default
        putchar(' ');
      }
    }

    putchar('\n');
  }

  atr_typeRender(ATR_DEFAULT); // Default
}

#endif /* ifndef ATR */
