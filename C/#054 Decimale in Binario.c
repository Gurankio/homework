/*
 * Jacopo Del Granchio
 * #054 04.12.2019
 *
 * Trasforma un numero decimale in binario.
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
void binario(int n, int v[], int l);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n;
  chiedi("Inserisci un numero: ", "%d", &n);

  int v[128] = {};
  binario(n, v, 128);

  bool firstOne = false;

  for (int i = 0; i < 128; i++) {
    if (v[i] == 1) firstOne = true;

    if (firstOne) printf("%d", v[i]);
  }

  printf("\n");

  // getchar();
  // system("pause");
  return 0;
}

void binario(int n, int v[], int l) {
  v[l - 1] = n % 2;
  n /= 2;

  if (n != 0) binario(n, v, l - 1);
}
