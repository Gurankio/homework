/*
 * Jacopo Del Granchio
 * #068 18.12.2019
 *
 * Stampa la somma e la media di un vettore.
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
void calcola(int [], int, float *, float *);

// Funzioni
int main() {
  setlocale(LC_ALL, "en_US");

  int n;
  chiedi("Inserisci la lunghezza: ", "%d", &n);

  int v[n];
  carica(v, n);

  float somma, media;
  calcola(v, n, &somma, &media);

  printf("Somma: %.0f Media: %.2f\n", somma, media);

  // getchar();
  // system("pause");
  return 0;
}

void calcola(int v[], int n, float *somma, float *media) {
  float sum = 0;

  for (int i = 0; i < n; ++i)
    sum += v[i];

  *somma = sum;
  *media = sum / n;
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
