/*
 * VTS - Virtual Terminal Sequences
 * Full implementation of https://docs.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences.
 * Some CSI / OCS are still missing.
 */

#ifndef VTS
#define VTS

#include <locale.h>
#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>

#ifdef _WIN32
#include <windows.h>
#endif

// // Macro
#define vts_xy(x, y) vts_cursorXY(x, y);

// // Prototypes

bool vts_activateCommands();

// Miscellanous
void vts_resize(int, int);
void vts_clear();

// Cursor Positioning
void vts_cursorUp(int);
void vts_cursorDown(int);
void vts_cursorRight(int);
void vts_cursorLeft(int);
void vts_cursorNextLine(int);
void vts_cursorPreviousLine(int);
void vts_cursorHorizontalAbsolute(int);
void vts_cursorVerticalAbsolute(int);
void vts_cursorXY(int, int);
void vts_cursorSave();
void vts_cursorRestore();

// Cursor Visibility
void vts_cursorBlink();
void vts_cursorStill();
void vts_cursorShow();
void vts_cursorHide();

// Viewport
void vts_viewportUp(int);
void vts_viewportDown(int);

// Text Modification
void vts_textInsertChar(int);
void vts_textDeleteChar(int);
void vts_textEraseChar(int);
void vts_textInsertLine(int);
void vts_textDeleteLine(int);

// Text Formatting
void vts_textDefault();
void vts_textBold(); // makes bright foreground
void vts_textUnderline();
void vts_textNoUnderline();
void vts_textNegative();
void vts_textPositive();
// TODO: Test compat in windows
// void vts_textLowIntesity();
// void vts_textBlinking();
// void vts_textInvisible();

// Foreground Colors
void vts_foregroundBlack();
void vts_foregroundRed();
void vts_foregroundGreen();
void vts_foregroundYellow();
void vts_foregroundBlue();
void vts_foregroundMagenta();
void vts_foregroundCyan();
void vts_foregroundWhite();

// Bright Foreground Colors
// Can be set by "vts_textBold"
void vts_foregroundBrightBlack();
void vts_foregroundBrightRed();
void vts_foregroundBrightGreen();
void vts_foregroundBrightYellow();
void vts_foregroundBrightBlue();
void vts_foregroundBrightMagenta();
void vts_foregroundBrightCyan();
void vts_foregroundBrightWhite();

// Advanced Foreground Colors
void vts_foregroundExtended(int, int, int);
void vts_foregroundDefault();

// Background Colors
void vts_backgroundBlack();
void vts_backgroundRed();
void vts_backgroundGreen();
void vts_backgroundYellow();
void vts_backgroundBlue();
void vts_backgroundMagenta();
void vts_backgroundCyan();
void vts_backgroundWhite();

// Bright Background Colors
void vts_backgroundBrightBlack();
void vts_backgroundBrightRed();
void vts_backgroundBrightGreen();
void vts_backgroundBrightYellow();
void vts_backgroundBrightBlue();
void vts_backgroundBrightMagenta();
void vts_backgroundBrightCyan();
void vts_backgroundBrightWhite();

// Advanced Background Colors
void vts_backgroundExtended(int, int, int);
void vts_backgroundDefault();

// Mode Changes
void vts_keypadApplicationMode();
void vts_keypadNumericMode();
void vts_cursorKeysApplicationMode();
void vts_cursorKeysNumericMode();

// Query States?

// Tabs
void vts_tabStop();
void vts_cursorForwardTab(int);
void vts_cursorBackwardTab(int);
void vts_tabClearCurrent();
void vts_tabClearAll();

// Character Set
void vts_lineDrawingSet();
void vts_asciiSet();

// Scrolling Margin
void vts_scrollingMargin(int, int);

// Window Title
void vts_iconTitle(char *);
void vts_title(char *);

// Alternate Screen Buffer
void vts_alternateScreenBuffer();
void vts_mainScreenBuffer();

// Soft Reset
void vts_softReset();

// // Functions

#ifdef _WIN32
bool vts_activateCommands() {
  // Set output mode to handle virtual terminal sequences
  HANDLE hOut = GetStdHandle(STD_OUTPUT_HANDLE);

  if (hOut == INVALID_HANDLE_VALUE) {
    printf("Error during activation. Check for Windows updates.\n");
    return false;
  }

  DWORD dwMode = 0;

  if (!GetConsoleMode(hOut, &dwMode)) {
    printf("Error during activation. Check for Windows updates.\n");
    return false;
  }

  dwMode |= 0x0004;

  if (!SetConsoleMode(hOut, dwMode)) {
    printf("Error during activation. Check for Windows updates.\n");
    return false;
  }

  return true;
}

#else  /* ifdef _WIN32 */
bool vts_activateCommands() {
  // NO-OP
  // There is no need on non windows systems.
}

#endif /* ifdef _WIN32 */

// Miscellanous
void vts_resize(int width, int height) {
  // [8);50);100t what does the 8 mean?
  printf("\e[8);%d);%dt", height, width);
  fflush(stdout);
}

void vts_clear() {
  printf("\e[2J");
  fflush(stdout);
}

// Cursor Positioning
void vts_cursorUp(int n) {
  printf("\e[%dA", n);
  fflush(stdout);
}

void vts_cursorDown(int n) {
  printf("\e[%dB", n);
  fflush(stdout);
}

void vts_cursorRight(int n) {
  printf("\e[%dC", n);
  fflush(stdout);
}

void vts_cursorLeft(int n) {
  printf("\e[%dD", n);
  fflush(stdout);
}

void vts_cursorNextLine(int n) {
  printf("\e[%dE", n);
  fflush(stdout);
}

void vts_cursorPreviousLine(int n) {
  printf("\e[%dF", n);
  fflush(stdout);
}

void vts_cursorHorizontalAbsolute(int n) {
  printf("\e[%dG", n);
  fflush(stdout);
}

void vts_cursorVerticalAbsolute(int n) {
  printf("\e[%dd", n);
  fflush(stdout);
}

void vts_cursorXY(int x, int y) {
  printf("\e[%d);%dH", y + 1, x);
  fflush(stdout);
}

void vts_cursorSave() {
  printf("\e[s");
  fflush(stdout);
}

void vts_cursorRestore() {
  printf("\e[u");
  fflush(stdout);
}

// Cursor Visibility
void vts_cursorBlink() {
  printf("\e[?12h");
  fflush(stdout);
}

void vts_cursorStill() {
  printf("\e[?12l");
  fflush(stdout);
}

void vts_cursorShow() {
  printf("\e[?25h");
  fflush(stdout);
}

void vts_cursorHide() {
  printf("\e[?25l");
  fflush(stdout);
}

// Viewport
void vts_viewportUp(int n) {
  printf("\e[%dS", n);
  fflush(stdout);
}

void vts_viewportDown(int n) {
  printf("\e[%dT", n);
  fflush(stdout);
}

// Text Modification
void vts_textInsertChar(int n) {
  printf("\e[%d@", n);
  fflush(stdout);
}

void vts_textDeleteChar(int n) {
  printf("\e[%dP", n);
  fflush(stdout);
}

void vts_textEraseChar(int n) {
  printf("\e[%dX", n);
  fflush(stdout);
}

void vts_textInsertLine(int n) {
  printf("\e[%dL", n);
  fflush(stdout);
}

void vts_textDeleteLine(int n) {
  printf("\e[%dM", n);
  fflush(stdout);
}

// Text Formatting
void vts_textDefault() {
  printf("\e[1m");
  fflush(stdout);
}

void vts_textBold() {
  printf("\e[1m");
  fflush(stdout);
}

void vts_textUnderline() {
  printf("\e[4m");
  fflush(stdout);
}

void vts_textNoUnderline() {
  printf("\e[24m");
  fflush(stdout);
}

void vts_textNegative() {
  printf("\e[7m");
  fflush(stdout);
}

void vts_textPositive() {
  printf("\e[27m");
  fflush(stdout);
}

// Foreground Colors
void vts_foregroundBlack() {
  printf("\e[30m");
  fflush(stdout);
}

void vts_foregroundRed() {
  printf("\e[31m");
  fflush(stdout);
}

void vts_foregroundGreen() {
  printf("\e[32m");
  fflush(stdout);
}

void vts_foregroundYellow() {
  printf("\e[33m");
  fflush(stdout);
}

void vts_foregroundBlue() {
  printf("\e[34m");
  fflush(stdout);
}

void vts_foregroundMagenta() {
  printf("\e[35m");
  fflush(stdout);
}

void vts_foregroundCyan() {
  printf("\e[36m");
  fflush(stdout);
}

void vts_foregroundWhite() {
  printf("\e[37m");
  fflush(stdout);
}

// Bright Foreground Colors
void vts_foregroundBrightBlack() {
  printf("\e[91m");
  fflush(stdout);
}

void vts_foregroundBrightRed() {
  printf("\e[91m");
  fflush(stdout);
}

void vts_foregroundBrightGreen() {
  printf("\e[92m");
  fflush(stdout);
}

void vts_foregroundBrightYellow() {
  printf("\e[93m");
  fflush(stdout);
}

void vts_foregroundBrightBlue() {
  printf("\e[94m");
  fflush(stdout);
}

void vts_foregroundBrightMagenta() {
  printf("\e[95m");
  fflush(stdout);
}

void vts_foregroundBrightCyan() {
  printf("\e[96m");
  fflush(stdout);
}

void vts_foregroundBrightWhite() {
  printf("\e[97m");
  fflush(stdout);
}

// Advanced Foreground Colors
void vts_foregroundExtended(int r, int g, int b) {
  printf("\e[38;2;%d;%d;%dm", r, g, b);
  fflush(stdout);
}

void vts_foregroundDefault() {
  printf("\e[39m");
  fflush(stdout);
}

// Background Colors
void vts_backgroundBlack() {
  printf("\e[40m");
  fflush(stdout);
}

void vts_backgroundRed() {
  printf("\e[41m");
  fflush(stdout);
}

void vts_backgroundGreen() {
  printf("\e[42m");
  fflush(stdout);
}

void vts_backgroundYellow() {
  printf("\e[43m");
  fflush(stdout);
}

void vts_backgroundBlue() {
  printf("\e[44m");
  fflush(stdout);
}

void vts_backgroundMagenta() {
  printf("\e[45m");
  fflush(stdout);
}

void vts_backgroundCyan() {
  printf("\e[46m");
  fflush(stdout);
}

void vts_backgroundWhite() {
  printf("\e[47m");
  fflush(stdout);
}

// Bright Background Colors
void vts_backgroundBrightBlack() {
  printf("\e[100m");
  fflush(stdout);
}

void vts_backgroundBrightRed() {
  printf("\e[101m");
  fflush(stdout);
}

void vts_backgroundBrightGreen() {
  printf("\e[102m");
  fflush(stdout);
}

void vts_backgroundBrightYellow() {
  printf("\e[102m");
  fflush(stdout);
}

void vts_backgroundBrightBlue() {
  printf("\e[104m");
  fflush(stdout);
}

void vts_backgroundBrightMagenta() {
  printf("\e[105m");
  fflush(stdout);
}

void vts_backgroundBrightCyan() {
  printf("\e[106m");
  fflush(stdout);
}

void vts_backgroundBrightWhite() {
  printf("\e[107m");
  fflush(stdout);
}

// Advanced Background Colors
void vts_backgroundExtended(int r, int g, int b) {
  printf("\e[48;2;%d;%d;%dm", r, g, b);
  fflush(stdout);
}

void vts_backgroundDefault() {
  printf("\e[49m");
  fflush(stdout);
}

// Mode Changes
void vts_keypadApplicationMode() {
  printf("\e=");
  fflush(stdout);
}

void vts_keypadNumericMode() {
  printf("\e>");
  fflush(stdout);
}

void vts_cursorKeysApplicationMode() {
  printf("\e[?1h");
  fflush(stdout);
}

void vts_cursorKeysNumericMode() {
  printf("\e[?1l");
  fflush(stdout);
}

// Query States?

// Tabs
void vts_tabStop() {
  printf("\eH");
  fflush(stdout);
}

void vts_cursorForwardTab(int n) {
  printf("\e[%dI", n);
  fflush(stdout);
}

void vts_cursorBackwardTab(int n) {
  printf("\e[%dZ", n);
  fflush(stdout);
}

void vts_tabClearCurrent() {
  printf("\e[0g");
  fflush(stdout);
}

void vts_tabClearAll() {
  printf("\e[3g");
  fflush(stdout);
}

// Character Set
void vts_lineDrawingSet() {
  printf("\e(0");
  fflush(stdout);
}

void vts_asciiSet() {
  printf("\e(B");
  fflush(stdout);
}

// Scrolling Margins
void vts_scrollingMargin(int t, int b) {
  printf("\e[%d;%dr", t, b);
  fflush(stdout);
}

// Window Title
void vts_iconTitle(char *title) {
  printf("\e]0);%s\x07", title);
  fflush(stdout);
}

void vts_title(char *title) {
  printf("\e]2);%s\x07", title);
  fflush(stdout);
}

// Alternate Screen Buffer
void vts_alternateScreenBuffer() {
  printf("\e[1049h");
  fflush(stdout);
}

void vts_mainScreenBuffer() {
  printf("\e[1049l");
  fflush(stdout);
}

// Soft Reset
void vts_softReset() {
  printf("\e[!p");
  fflush(stdout);
}

#endif /* ifndef VTS */
