#include "speech_vad.h"

simple_vad *vad;
struct periods *per;
struct cut_info *cut;

const char output_filename_prefix[] = "16k_1.pcm"; // 保存的文件名
const char output_dir[] = "/sdcard/cmakedemo/output_pcm"; // 保存的目录


/**
 * 处理音频数据
 * @param frame 数据frame
 * @return -1：表示传入的数据frame为空
 *         -2：表示vad未初始化，请先执行vad_open
 *         0：
 */
char* vad_process_frame(int16_t *frame){
    if(vad == NULL)
        return "-1";

    char* ret = process_frame(0, vad, frame, per, cut);

    return ret;
}

int vad_open() {
    vad = simple_vad_create();
    per = periods_create();
    cut = cut_info_create();
    snprintf(cut->output_filename_prefix, sizeof(cut->output_filename_prefix), "%s",
             output_filename_prefix);
    snprintf(cut->output_file_dir, sizeof(cut->output_file_dir), "%s",
             output_dir);

}

int vad_close(){
    if(vad != NULL)
        simple_vad_free(vad);
    if(per != NULL)
        periods_free(per);
    if(cut != NULL)
        cut_info_free(cut);
}
