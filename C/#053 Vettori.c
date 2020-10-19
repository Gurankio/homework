/*
 * Jacopo Del Granchio
 * #053 03.12.2019
 *
 * Vettori.
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
char * string_format(char *format, ...);
void azzera(int n, int v[]);
void riempi(int n, int v[n]);
int max(int n, int v[]);
int min(int n, int v[]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n;
  chiedi("Inserisci la lunghezza del vettore: ", "%d", &n);

  int v[n];
  azzera(n, v);
  riempi(n, v);

  int minV = min(n, v), maxV = max(n, v);
  printf("Il minimo è %d\n", minV);
  printf("Il massimo è %d\n", maxV);

  // getchar();
  // system("pause");
  return 0;
}

char * string_format(char *format, ...) {
  char *result = (char *)malloc(128 * sizeof(result));
  va_list list;

  va_start(list, format);
  vsnprintf(result, 128, format, list);
  va_end(list);

  return result;
}

void azzera(int n, int v[]) {
  for (int i = 0; i < n; i++) v[i] = 0;
}

void riempi(int n, int v[n]) {
  for (int i = 0; i < n; i++) {
    //
    chiedi(string_format("Inserisci il %d elemento del vettore: ", i), "%d", &v[i]);
    //
  }
}

int max(int n, int v[]) {
  int max;

  for (int i = 0; i < n; i++) if (v[i] > max) max = v[i];

  return max;
}

int min(int n, int v[]) {
  int min;

  for (int i = 0; i < n; i++) if (v[i] < min) min = v[i];

  return min;
}
