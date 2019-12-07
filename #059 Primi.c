/*
 * Jacopo Del Granchio
 * #059 11.12.2019
 *
 * Calcola i numeri primi tra 1 ed un numero dato.
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
bool isPrime(int);
int primes(int n);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n;
  chiedi("Insesrisci un numero: ", "%d", &n);
  printf("Ci sono %d numeri primi tra 1 e %d\n", primes(n), n);

  // getchar();
  // system("pause");
  return 0;
}

bool isPrime(int n) {
  int counter = 0;

  for (int i = 2; i < n; i++) counter += (n % i == 0);

  return !counter;
}

int primes(int n) {
  int t, c = 0;

  for (int i = 1; i < n; i++) {
    t = isPrime(i);
    c += t;

    if (t) {
      printf("%d\t", i);

      if (c % 10 == 0) printf("\n");
    }
  }

  printf("\n");
  return c;
}
