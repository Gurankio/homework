/*
 * Jacopo Del Granchio
 * #071 18.12.2019
 *
 * Controlla se un vettore è crescente o decrescente.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <time.h>

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int esiste(int v[], int n, int x);
void caricaRand(int v[], int n, int);
void scarica(int v[], int n);

bool crescente(int v[], int n);
bool decrescente(int v[], int n);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n;
  chiedi("Inserisci la lunghezza: ", "%d", &n);

  int v[n];
  caricaRand(v, n, n);
  scarica(v, n);

  if (crescente(v, n)) printf("Crescente.\n");
  else if (decrescente(v, n)) printf("Decrescente.\n");
  else printf("Ne crescente ne decrescente.\n");

  // getchar();
  // system("pause");
  return 0;
}

bool crescente(int v[], int n) {
  bool r = 1;

  for (int i = 1; i < n; i++)
    if (v[i - 1] > v[i]) r = 0;

  return r;
}

bool decrescente(int v[], int n) {
  bool r = 1;

  for (int i = 1; i < n; i++)
    if (v[i - 1] < v[i]) r = 0;

  return r;
}

int esiste(int v[], int n, int x) {
  int r = 0;

  for (int i = 0; i < n; i++)
    if (v[i] == x) r += 1;

  return r != 0;
}

void caricaRand(int v[], int n, int max) {
  srand((unsigned)time(NULL) + rand());

  v[0] = rand() % max;

  for (int i = 1; i < n; i++) {
    do v[i] = rand() % max;
    while (esiste(v, i - 1, v[i]));
  }
}

void scarica(int v[], int n) {
  printf("[ ");

  for (int i = 0; i < n; i++)
    printf("%d%s", v[i], i != n - 1 ? ", " : "");

  printf(" ]\n");
}
