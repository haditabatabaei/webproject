
#include "libmscore/score.h"
#include "synthesizer/msynthesizer.h"
#include "libmscore/note.h"
#include "musescore.h"
#include "libmscore/part.h"
#include "preferences.h"
#include "exportmp3.h"

namespace Ms {


MP3Exporter::MP3Exporter()
      {
      mLibraryLoaded = false;
      mEncoding = false;
      mGF = NULL;

      QSettings settings;
      mLibPath = settings.value("/Export/lameMP3LibPath", "").toString();

      mBitrate = 128;
      mQuality = QUALITY_2;
      mChannel = CHANNEL_JOINT;
      mMode = MODE_CBR;
      mRoutine = ROUTINE_FAST;
      }

MP3Exporter::~MP3Exporter()
      {
      freeLibrary();
      }


bool MP3Exporter::findLibrary()
      {
      QString path;
      QString name;

      if (!mLibPath.isEmpty()) {
            QFileInfo fi(mLibPath);
            path = fi.absolutePath();
            name = fi.completeBaseName();
            }
      else {
            path = getLibraryPath();
            name = getLibraryName();
            }

      if (MScore::noGui)
            return false;

      QString libPath = QFileDialog::getOpenFileName(
           0, qApp->translate("MP3Exporter", "Where is %1 ?").arg(getLibraryName()),
           path,
           getLibraryTypeString(),
           0,
           preferences.getBool(PREF_UI_APP_USENATIVEDIALOGS) ? QFileDialog::Options() : QFileDialog::DontUseNativeDialog
           );

      if (libPath.isEmpty())
            return false;

      QFileInfo fp(libPath);
      if (!fp.exists())
            return false;

      mLibPath = libPath;

      QSettings settings;
      settings.setValue("/Export/lameMP3LibPath", mLibPath);

      return true;
      }


bool MP3Exporter::loadLibrary(AskUser askuser)
      {
      if (validLibraryLoaded()) {
            freeLibrary();
            mLibraryLoaded = false;
            }

            if (!mLibPath.isEmpty()) {
            qDebug("Attempting to load LAME from previously defined path");
            mLibraryLoaded = initLibrary(mLibPath);
            }

            if (!validLibraryLoaded()) {
            qDebug("Attempting to load LAME from system search paths");
            mLibPath = getLibraryName();
            mLibraryLoaded = initLibrary(mLibPath);
            }

            if (!validLibraryLoaded()) {
            qDebug("Attempting to load LAME from builtin path");
            QFileInfo fn(QDir(getLibraryPath()), getLibraryName());
            mLibPath = fn.absoluteFilePath();
            mLibraryLoaded = initLibrary(mLibPath);
            }

            if (!validLibraryLoaded() && askuser != MP3Exporter::AskUser::NO) {
            qDebug("(Maybe) ask user for library");
            int ret = QMessageBox::question(0, qApp->translate("MP3Exporter", "Save as MP3"),
                  qApp->translate("MP3Exporter", "MuseScore does not export MP3 files directly, but instead uses "
                   "the freely available LAME library.  You must obtain %1 "
                   "separately (for details check the handbook), and then locate the file for MuseScore.\n"
                   "You only need to do this once.\n\n"
                   "Would you like to locate %2 now?").arg(getLibraryName()).arg(getLibraryName()),
                   QMessageBox::Yes|QMessageBox::No, QMessageBox::NoButton);
            if (ret == QMessageBox::Yes && findLibrary()) {
                  mLibraryLoaded = initLibrary(mLibPath);
                  }
            }

            if (!validLibraryLoaded()) {
            qDebug("Failed to locate LAME library");
            return false;
            }

      qDebug("LAME library successfully loaded");
      return true;
      }

bool MP3Exporter::validLibraryLoaded()
      {
      return mLibraryLoaded;
      }

void MP3Exporter::setMode(int mode)
      {
      mMode = mode;
      }

void MP3Exporter::setBitrate(int rate)
      {
      mBitrate = rate;
      }

void MP3Exporter::setQuality(int q, int r)
      {
      mQuality = q;
      mRoutine = r;
      }

void MP3Exporter::setChannel(int mode)
      {
      mChannel = mode;
      }


bool MP3Exporter::initLibrary(QString libpath)
      {
      qDebug("Loading LAME from %s", qPrintable(libpath));
      lame_lib = new QLibrary(libpath, 0);
      if (!lame_lib->load()) {
            qDebug("load failed <%s>", qPrintable(lame_lib->errorString()));
            return false;
            }

      

      lame_init = (lame_init_t *)
        lame_lib->resolve("lame_init");
      get_lame_version = (get_lame_version_t *)
        lame_lib->resolve("get_lame_version");
      lame_init_params = (lame_init_params_t *)
        lame_lib->resolve("lame_init_params");
      lame_encode_buffer_float = (lame_encode_buffer_float_t *)
        lame_lib->resolve("lame_encode_buffer_float");
      lame_encode_flush = (lame_encode_flush_t *)
        lame_lib->resolve("lame_encode_flush");
      lame_close = (lame_close_t *)
        lame_lib->resolve("lame_close");

      lame_set_in_samplerate = (lame_set_in_samplerate_t *)
         lame_lib->resolve("lame_set_in_samplerate");
      lame_set_out_samplerate = (lame_set_out_samplerate_t *)
         lame_lib->resolve("lame_set_out_samplerate");
      lame_set_num_channels = (lame_set_num_channels_t *)
         lame_lib->resolve("lame_set_num_channels");
      lame_set_quality = (lame_set_quality_t *)
         lame_lib->resolve("lame_set_quality");
      lame_set_brate = (lame_set_brate_t *)
         lame_lib->resolve("lame_set_brate");
      lame_set_VBR = (lame_set_VBR_t *)
         lame_lib->resolve("lame_set_VBR");
      lame_set_VBR_q = (lame_set_VBR_q_t *)
         lame_lib->resolve("lame_set_VBR_q");
      lame_set_VBR_min_bitrate_kbps = (lame_set_VBR_min_bitrate_kbps_t *)
         lame_lib->resolve("lame_set_VBR_min_bitrate_kbps");
      lame_set_mode = (lame_set_mode_t *)
         lame_lib->resolve("lame_set_mode");
      lame_set_preset = (lame_set_preset_t *)
         lame_lib->resolve("lame_set_preset");
      lame_set_error_protection = (lame_set_error_protection_t *)
         lame_lib->resolve("lame_set_error_protection");
      lame_set_disable_reservoir = (lame_set_disable_reservoir_t *)
         lame_lib->resolve("lame_set_disable_reservoir");
      lame_set_padding_type = (lame_set_padding_type_t *)
         lame_lib->resolve("lame_set_padding_type");
      lame_set_bWriteVbrTag = (lame_set_bWriteVbrTag_t *)
         lame_lib->resolve("lame_set_bWriteVbrTag");

            lame_get_lametag_frame = (lame_get_lametag_frame_t *)
         lame_lib->resolve("lame_get_lametag_frame");
      lame_mp3_tags_fid = (lame_mp3_tags_fid_t *)
         lame_lib->resolve("lame_mp3_tags_fid");
#if defined(Q_OS_WIN)
      beWriteInfoTag = (beWriteInfoTag_t *)
         lame_lib->resolve("beWriteInfoTag");
      beVersion = (beVersion_t *)
         lame_lib->resolve("beVersion");
#endif

      if (!lame_init ||
        !get_lame_version ||
        !lame_init_params ||
        !lame_encode_buffer_float ||
        !lame_encode_flush ||
        !lame_close ||
        !lame_set_in_samplerate ||
        !lame_set_out_samplerate ||
        !lame_set_num_channels ||
        !lame_set_quality ||
        !lame_set_brate ||
        !lame_set_VBR ||
        !lame_set_VBR_q ||
        !lame_set_mode ||
        !lame_set_preset ||
        !lame_set_error_protection ||
        !lame_set_disable_reservoir ||
        !lame_set_padding_type ||
        !lame_set_bWriteVbrTag) {
            qDebug("Failed to find a required symbol in the LAME library");
#if defined(Q_OS_WIN)
            if (beVersion) {
                  be_version v;
                  beVersion(&v);

                  mBladeVersion = QString("You are linking to lame_enc.dll v%d.%d. This version is not compatible with MuseScore %d.\nPlease download the latest version of the LAME MP3 library.")
                                .arg(v.byMajorVersion)
                                .arg(v.byMinorVersion)
                                .arg(1);                   }
#endif

            lame_lib->unload();
            delete lame_lib;
            return false;
            }

      mGF = lame_init();
      if (mGF == NULL) {
            lame_lib->unload();
            delete lame_lib;
            return false;
            }

      return true;
      }


void MP3Exporter::freeLibrary()
      {
      if (mGF) {
            lame_close(mGF);
            mGF = NULL;
            lame_lib->unload();
            delete lame_lib;
            }
      return;
      }


QString MP3Exporter::getLibraryVersion()
      {
      if (!mLibraryLoaded)
            return QString("");
      return QString("LAME %s").arg(get_lame_version());
      }


int MP3Exporter::initializeStream(int channels, int sampleRate)
      {
      if (!mLibraryLoaded)
            return -1;

      if (channels > 2)
            return -1;

      lame_set_error_protection(mGF, false);
      lame_set_num_channels(mGF, channels);
      lame_set_in_samplerate(mGF, sampleRate);
      lame_set_out_samplerate(mGF, sampleRate);
      lame_set_disable_reservoir(mGF, true);
      lame_set_padding_type(mGF, PAD_NO);

                  lame_set_bWriteVbrTag(mGF, true);

            switch (mMode) {
            case MODE_SET:
                  {
                  int preset;

                  if (mQuality == PRESET_INSANE)
                        preset = INSANE;
                  else if (mRoutine == ROUTINE_FAST) {
                        if (mQuality == PRESET_EXTREME)
                              preset = EXTREME_FAST;
                        else if (mQuality == PRESET_STANDARD)
                              preset = STANDARD_FAST;
                        else
                              preset = 1007;                            }
                  else {
                        if (mQuality == PRESET_EXTREME)
                              preset = EXTREME;
                        else if (mQuality == PRESET_STANDARD)
                              preset = STANDARD;
                        else
                              preset = 1006;                            }
                  lame_set_preset(mGF, preset);
                  }
                  break;

            case MODE_VBR:
                  lame_set_VBR(mGF, (mRoutine == ROUTINE_STANDARD ? vbr_rh : vbr_mtrh ));
                  lame_set_VBR_q(mGF, mQuality);
                  break;

            case MODE_ABR:
                  lame_set_preset(mGF, mBitrate );
                  break;

            default:
                  lame_set_VBR(mGF, vbr_off);
                  lame_set_brate(mGF, mBitrate);
                  break;
            }

            MPEG_mode mode;
      if (channels == 1)
            mode = MONO;
      else if (mChannel == CHANNEL_JOINT)
            mode = JOINT_STEREO;
      else
            mode = STEREO;
      lame_set_mode(mGF, mode);

      int rc = lame_init_params(mGF);
      if (rc < 0)
            return rc;

#if 0
      dump_config(mGF);
#endif

      mInfoTagLen = 0;
      mEncoding = true;

      return mSamplesPerChunk;
      }


int MP3Exporter::getOutBufferSize()
      {
      if (!mEncoding)
            return -1;

      return mOutBufferSize;
      }


void MP3Exporter::bufferPreamp(float buffer[], int nSamples)
      {
      for (int i = 0; i < nSamples; i++)
            buffer[i] = buffer[i] * 32768;
      }


int MP3Exporter::encodeBuffer(float inbufferL[], float inbufferR[], unsigned char outbuffer[])
      {
      if (!mEncoding)
            return -1;

      bufferPreamp(inbufferL, mSamplesPerChunk);
      bufferPreamp(inbufferR, mSamplesPerChunk);
      return lame_encode_buffer_float(mGF, inbufferL, inbufferR, mSamplesPerChunk,
         outbuffer, mOutBufferSize);
      }


int MP3Exporter::encodeRemainder(float inbufferL[], float inbufferR[], int nSamples,
   unsigned char outbuffer[])
      {
      if (!mEncoding)
            return -1;

      bufferPreamp(inbufferL, nSamples);
      bufferPreamp(inbufferR, nSamples);
      return lame_encode_buffer_float(mGF, inbufferL, inbufferR, nSamples, outbuffer,
         mOutBufferSize);
      }


int MP3Exporter::encodeBufferMono(float inbuffer[], unsigned char outbuffer[])
      {
      if (!mEncoding)
            return -1;

      bufferPreamp(inbuffer, mSamplesPerChunk);
      return lame_encode_buffer_float(mGF, inbuffer, inbuffer, mSamplesPerChunk,
         outbuffer, mOutBufferSize);
      }


int MP3Exporter::encodeRemainderMono(float inbuffer[], int nSamples,
   unsigned char outbuffer[])
      {
      if (!mEncoding)
            return -1;

      bufferPreamp(inbuffer, nSamples);
      return lame_encode_buffer_float(mGF, inbuffer, inbuffer, nSamples, outbuffer,
         mOutBufferSize);
      }


int MP3Exporter::finishStream(unsigned char outbuffer[])
      {
      if (!mEncoding)
            return -1;

      mEncoding  = false;
      int result = lame_encode_flush(mGF, outbuffer, mOutBufferSize);

      if (lame_get_lametag_frame)
            mInfoTagLen = lame_get_lametag_frame(mGF, mInfoTagBuf, sizeof(mInfoTagBuf));
      return result;
      }


void MP3Exporter::cancelEncoding()
      {
      mEncoding = false;
      }



#if defined(Q_OS_WIN)


QString MP3Exporter::getLibraryPath()
      {
      QSettings settings("HKEY_LOCAL_MACHINE\\Software\\Lame for Audacity", QSettings::NativeFormat);
      QString sReturnedValue = settings.value( "InstallPath", "" ).toString();
      if (! sReturnedValue.isEmpty()) {
            return sReturnedValue;
            }
      return QDir::rootPath();
      }

QString MP3Exporter::getLibraryName()
      {
      return QString("lame_enc.dll");
      }

QString MP3Exporter::getLibraryTypeString()
      {
      return QString("Only lame_enc.dll (lame_enc.dll);;Dynamically Linked Libraries (*.dll);;All Files (*.*)");
      }

#elif defined(Q_OS_MAC)


QString MP3Exporter::getLibraryPath()
      {
      return QString("/usr/local/lib/audacity");
      }

QString MP3Exporter::getLibraryName()
      {
      return QString("libmp3lame.dylib");
      }

QString MP3Exporter::getLibraryTypeString()
      {
      return QString("Only libmp3lame.dylib (libmp3lame.dylib);;Dynamic Libraries (*.dylib);;All Files (*)");
      }

#else 

QString MP3Exporter::getLibraryPath()
      {
      return QString("/usr/lib");
      }

QString MP3Exporter::getLibraryName()
      {
      return QString("libmp3lame.so.0");
      }

QString MP3Exporter::getLibraryTypeString()
      {
      return QString("Only libmp3lame.so.0 (libmp3lame.so.0);;Primary Shared Object files (*.so);;Extended Libraries (*.so*);;All Files (*)");
      }
#endif 
}

