//
// Created by exlink on 2018/8/29.
//
#include <stdio.h>
#include <common_audio/vad/include/webrtc_vad.h>
#include <stdlib.h>
#include "simple_vad.h"
#include "period_format.h"
#include "file_cut.h"
#include <android/log.h>
#include <string.h>

#ifndef CMAKEDEMO_MAIN_H
#define CMAKEDEMO_MAIN_H

#endif //CMAKEDEMO_MAIN_H

char * docutf();

int run(FILE *fp, simple_vad *vad, struct cut_info *cut);

int add_period_activity(struct periods *per, int is_active, int is_last);
