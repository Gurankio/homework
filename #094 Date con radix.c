/*
 * Jacopo Del Granchio
 * #094 15.02.2020
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


typedef struct {
  int anno;
  int mese;
  int giorno;
} data;

// Prototipi


// Funzioni
int main() {
  setlocale(LC_ALL, "");






  // getchar();
  // system("pause");
  return 0;
}
