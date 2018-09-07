#include <stdio.h>
#include <common_audio/vad/include/webrtc_vad.h>
#include <stdlib.h>
#include "simple_vad.h"
#include "period_format.h"
#include "file_cut.h"
#include <android/log.h>
#include <string.h>
#include "main.h"

#ifndef CMAKEDEMO_MAIN_H
#define CMAKEDEMO_MAIN_H

#endif //CMAKEDEMO_MAIN_H



int vad_process_frame(int16_t *frame);

int vad_open();

int vad_close();