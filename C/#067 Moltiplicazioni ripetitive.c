/*
 * Jacopo Del Granchio
 * #067 18.12.2019
 *
 * Calcola la moltiplicazione di un elemento di un vettore e del suo successivo
 * salvandoli in un secondo vettore.
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
void azzera(double v[], int n);
void carica(double v[], int n);
void scarica(double v[], int n);
void calcola(double [], int, double[], int *);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n;
  chiedi("Inserisci la lunghezza: ", "%d", &n);

  double v[n];
  carica(v, n);

  double r[n / 2];
  int i = 0;
  calcola(v, n, r, &i);
  scarica(r, i);

  // getchar();
  // system("pause");
  return 0;
}

void calcola(double v[], int n, double r[], int *rI) {
  for (int i = 0; i < n; i += 2)
    r[(*rI)++] = v[i] * v[i + 1];
}

void azzera(double v[], int n) {
  for (int i = 0; i < n; i++) v[i] = 0;
}

void carica(double v[], int n) {
  for (int i = 0; i < n; i++) {
    printf("Inserisci il %d elemento del vettore: ", i);
    scanf("%lf", &v[i]);
  }
}

void scarica(double v[], int n) {
  printf("[ ");

  for (int i = 0; i < n; i++)
    printf("%.2lf%s", v[i], i != n - 1 ? ", " : "");

  printf(" ]\n");
}
