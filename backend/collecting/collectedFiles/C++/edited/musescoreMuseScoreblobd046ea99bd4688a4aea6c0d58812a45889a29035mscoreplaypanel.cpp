
#include "playpanel.h"
#include "libmscore/sig.h"
#include "libmscore/score.h"
#include "libmscore/repeatlist.h"
#include "seq.h"
#include "musescore.h"
#include "libmscore/measure.h"

namespace Ms {


PlayPanel::PlayPanel(QWidget* parent)
   : QWidget(parent, Qt::Dialog)
      {
      setObjectName("PlayPanel");
      cachedTickPosition = -1;
      cachedTimePosition = -1;
      cs                 = 0;
      tempoSliderIsPressed = false;
      setupUi(this);
      setWindowFlags(Qt::Tool);
      setWindowFlags(this->windowFlags() & ~Qt::WindowContextHelpButtonHint);

      MuseScore::restoreGeometry(this);

      setScore(0);

      playButton->setDefaultAction(getAction("play"));
      rewindButton->setDefaultAction(getAction("rewind"));
      countInButton->setDefaultAction(getAction("countin"));
      metronomeButton->setDefaultAction(getAction("metronome"));
      loopButton->setDefaultAction(getAction("loop"));
      loopInButton->setDefaultAction(getAction("loop-in"));
      loopOutButton->setDefaultAction(getAction("loop-out"));
      enablePlay = new EnablePlayForWidget(this);

      tempoSlider->setDclickValue1(100.0);
      tempoSlider->setDclickValue2(100.0);
      tempoSlider->setUseActualValue(true);

      mgainSlider->setValue(seq->metronomeGain());
      mgainSlider->setDclickValue1(seq->metronomeGain() - 10.75f);
      mgainSlider->setDclickValue2(seq->metronomeGain() - 10.75f);

      connect(volumeSlider, SIGNAL(valueChanged(double,int)), SLOT(volumeChanged(double,int)));
      connect(mgainSlider,  SIGNAL(valueChanged(double,int)), SLOT(metronomeGainChanged(double,int)));
      connect(posSlider,    SIGNAL(sliderMoved(int)),         SLOT(setPos(int)));
      connect(tempoSlider,  SIGNAL(valueChanged(double,int)), SLOT(relTempoChanged(double,int)));
      connect(tempoSlider,  SIGNAL(sliderPressed(int)),       SLOT(tempoSliderPressed(int)));
      connect(tempoSlider,  SIGNAL(sliderReleased(int)),      SLOT(tempoSliderReleased(int)));
      connect(relTempoBox,  SIGNAL(valueChanged(double)),     SLOT(relTempoChanged()));
      connect(seq,          SIGNAL(heartBeat(int,int,int)),   SLOT(heartBeat(int,int,int)));
      }

PlayPanel::~PlayPanel()
      {
                        if (isVisible()) {
            MuseScore::saveGeometry(this);
            }
      }


void PlayPanel::relTempoChanged(double d, int)
      {
      double relTempo = d * .01;
      emit relTempoChanged(relTempo);
            if (relTempo < 1.01 && relTempo > 0.99) {
            relTempo = 1.00;
            }
      setTempo(seq->curTempo() * relTempo);
      setRelTempo(relTempo);
      }


void PlayPanel::relTempoChanged()
      {
      double v = relTempoBox->value();
      tempoSlider->setValue(v);
      relTempoChanged(v, 0);
      }


void PlayPanel::closeEvent(QCloseEvent* ev)
      {
      emit closed(false);
      QWidget::closeEvent(ev);
      }


void PlayPanel::hideEvent(QHideEvent* ev)
      {
      MuseScore::saveGeometry(this);
      QWidget::hideEvent(ev);
      }


void PlayPanel::showEvent(QShowEvent* e)
      {
      enablePlay->showEvent(e);
      QWidget::showEvent(e);
      activateWindow();
      setFocus();
      }


bool PlayPanel::eventFilter(QObject* obj, QEvent* e)
      {
      if (enablePlay->eventFilter(obj, e))
            return true;
      return QWidget::eventFilter(obj, e);
      }

void PlayPanel::keyPressEvent(QKeyEvent* ev) {
      if (ev->key() == Qt::Key_Escape && ev->modifiers() == Qt::NoModifier) {
            close();
            return;
            }
      QWidget::keyPressEvent(ev);
      }


void PlayPanel::setScore(Score* s)
      {
      if (cs != 0 && cs == s)
            return;
      cs = s;
      bool enable = cs != 0;
      volumeSlider->setEnabled(enable);
      posSlider->setEnabled(enable);
      tempoSlider->setEnabled(enable);
      if (cs && seq && seq->canStart()) {
            setTempo(cs->tempomap()->tempo(0));
            setRelTempo(cs->tempomap()->relTempo());
            setEndpos(cs->repeatList()->ticks());
            int tick = cs->pos(POS::CURRENT);
            heartBeat(tick, tick, 0);
            }
      else {
            setTempo(2.0);
            setRelTempo(1.0);
            setEndpos(0);
            heartBeat(0, 0, 0);
            updatePosLabel(0);
            }
      update();
      }


void PlayPanel::setEndpos(int val)
      {
      posSlider->setRange(0, val);
      }


void PlayPanel::setTempo(double val)
      {
      int tempo = lrint(val * 60.0);
      tempoLabel->setText(QString("%1 BPM").arg(tempo, 3, 10, QLatin1Char(' ')));
      }


void PlayPanel::setRelTempo(qreal val)
      {
      val *= 100;
      relTempoBox->setValue(val);
      tempoSlider->setValue(val);
      }


void PlayPanel::setGain(float val)
      {
      volumeSlider->setValue(val);
      }


void PlayPanel::volumeChanged(double val, int)
      {
      emit gainChange(val);
      }


void PlayPanel::metronomeGainChanged(double val, int)
      {
      emit metronomeGainChanged(val);
      }


void PlayPanel::setPos(int utick)
      {
      if (!cs)
            return;
      if (cachedTickPosition != utick)
            emit posChange(utick);
      updatePosLabel(utick);
      updateTimeLabel(cs->utick2utime(utick));
      }


void PlayPanel::heartBeat(int , int utick, int samples)
      {
      if (cachedTickPosition != utick) {
            updatePosLabel(utick);
            posSlider->setValue(utick);
            }
      int sec = samples/MScore::sampleRate;
      if (sec == cachedTimePosition)
            return;
      updateTimeLabel(sec);
      }


void PlayPanel::updateTimeLabel(int sec)
      {
      cachedTimePosition = sec;
      int m              = sec / 60;
      sec                = sec % 60;
      int h              = m / 60;
      m                  = m % 60;
      char buffer[32];
      sprintf(buffer, "%d:%02d:%02d", h, m, sec);
      timeLabel->setText(QString(buffer));
      }


void PlayPanel::updatePosLabel(int utick)
      {
      cachedTickPosition = utick;
      int bar = 0;
      int beat = 0;
      int t = 0;
      int tick = 0;
      if (cs) {
            tick = cs->repeatList()->utick2tick(utick);
            cs->sigmap()->tickValues(tick, &bar, &beat, &t);
            double tpo = cs->tempomap()->tempo(tick) * cs->tempomap()->relTempo();
            setTempo(tpo);
            }
      char buffer[32];
      sprintf(buffer, "%03d.%02d", bar+1, beat+1);
      posLabel->setText(QString(buffer));
      }


void PlayPanel::tempoSliderPressed(int)
      {
      tempoSliderIsPressed = true;
      }


void PlayPanel::tempoSliderReleased(int)
      {
      tempoSliderIsPressed = false;
      }


void PlayPanel::changeEvent(QEvent *event)
      {
      QWidget::changeEvent(event);
      if (event->type() == QEvent::LanguageChange)
            retranslate();
      }

}

