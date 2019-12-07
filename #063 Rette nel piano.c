/*
 * Jacopo Del Granchio
 * #063 11.12.2019
 *
 * Controlla se due rette sono parallele o perpendicolari.
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
bool parallele(float, float, float, float);
bool perpendicolari(float, float, float, float);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  float mA, qA, mB, qB;
  chiedi("Inserisci il coefficente angolare della prima retta: ", "%f", &mA);
  chiedi("Inserisci l'ordinata all'origine della prima retta: ", "%f", &qA);
  chiedi("Inserisci il coefficente angolare della seconda retta: ", "%f", &mB);
  chiedi("Inserisci l'ordinata all'origine della seconda retta: ", "%f", &qB);

  if (parallele(mA, qA, mB, qB)) printf("Le rette sono parallele\n");

  if (perpendicolari(mA, qA, mB, qB)) printf("Le rette sono perpendicolari\n");

  // getchar();
  // system("pause");
  return 0;
}

bool parallele(float mA, float qA, float mB, float qB) {
  return mA == mB && qA != qB;
}

bool perpendicolari(float mA, float qA, float mB, float qB) {
  return mA == 1 / mB;
}
