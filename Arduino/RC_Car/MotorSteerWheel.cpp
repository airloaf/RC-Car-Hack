#include "MotorSteerWheel.h"

MotorSteerWheel::MotorSteerWheel(int pin, int speed) {
  m_motorPin = pin;

  m_motor = new AF_DCMotor(pin);

  m_direction = STEER_STRAIGHT;

  setSpeed(speed);

}

MotorSteerWheel::~MotorSteerWheel() {
  delete m_motor;
  m_motor = nullptr;
}

void MotorSteerWheel::setSpeed(int speed) {
  m_motor->setSpeed(speed);
}

void MotorSteerWheel::setDirection(int direction) {
  if (m_direction == direction){
    m_direction = STEER_STRAIGHT;
  }else
    m_direction = direction;

  update();
}

void MotorSteerWheel::update() {
  int setting;

  switch (m_direction) {
    case STEER_LEFT:
      setting = FORWARD;
      break;
    case STEER_RIGHT:
      setting = BACKWARD;
      break;
    default:
      setting = RELEASE;
      break;
  }

  m_motor->run(setting);

}

