/*
 * Jacopo Del Granchio
 * #058 04.12.2019
 *
 * Calcola l'intersezione di due vettori in uno solo.
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
void azzera(int v[], int n);
void carica(int v[], int n);
void scarica(int v[], int n);
int differenza(int a[], int x, int b[], int y, int c[]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int x, y;
  chiedi("Inserire la lunghezza del vettore 1: ", "%d", &x);
  chiedi("Inserire la lunghezza del vettore 2: ", "%d", &y);

  int a[x], b[y], c[(x > y ? x : y)];
  carica(a, x);
  carica(b, y);
  azzera(c, (x > y ? x : y));

  scarica(a, x);
  scarica(b, y);

  int l = differenza(a, x, b, y, c);
  scarica(c, l);

  // getchar();
  // system("pause");
  return 0;
}

void azzera(int v[], int n) {
  for (int i = 0; i < n; i++) v[i] = 0;
}

void carica(int v[], int n) {
  for (int i = 0; i < n; i++) {
    printf("Inserisci il %d elemento del vettore: ", i);
    scanf("%d", &v[i]);
  }
}

void scarica(int v[], int n) {
  printf("[ ");

  for (int i = 0; i < n; i++)
    printf("%d%s", v[i], i != n - 1 ? ", " : "");

  printf(" ]\n");
}

int differenza(int a[], int x, int b[], int y, int c[]) {
  int i = 0;

  for (int j = 0; j < x; ++j) {
    bool found = 0;

    for (int k = 0; k < y; ++k)
      if (a[j] == b[k]) found = 1;

    if (!found) c[i++] = a[j];
  }

  for (int j = 0; j < y; ++j) {
    bool found = 0;

    for (int k = 0; k < x; ++k)
      if (b[j] == a[k]) found = 1;

    if (!found) c[i++] = b[j];
  }

  return i;
}
