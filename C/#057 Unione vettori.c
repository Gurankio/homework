/*
 * Jacopo Del Granchio
 * #057 04.12.2019
 *
 * Unisce due vettori in uno solo.
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
void carica(int v[], int n);
void scarica(int v[], int n);
void unisce(int a[], int x, int b[], int y, int c[]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int x, y;
  chiedi("Inserire la lunghezza del vettore 1: ", "%d", &x);
  chiedi("Inserire la lunghezza del vettore 2: ", "%d", &y);

  int a[x], b[y], c[x + y];
  carica(a, x);
  carica(b, y);

  scarica(a, x);
  scarica(b, y);


  unisce(a, x, b, y, c);
  scarica(c, x + y);

  // getchar();
  // system("pause");
  return 0;
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

void unisce(int a[], int x, int b[], int y, int c[]) {
  int min = x < y ? x : y;
  int i = 0;

  for (int j = 0; j < min; j++) {
    c[i++] = a[j];
    c[i++] = b[j];
  }

  if (x < y)
    for (int j = x; j < y; j++)
      c[i++] = b[j];

  else
    for (int j = y; j < x; j++)
      c[i++] = a[j];
}
