/*
 * Copyright (C) 2019 ProSiebenSat1.Digital GmbH.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
#include "JniRefStats.h"

void JniRefStats::clear() {
  m_count = 0;
}

void JniRefStats::add() {
#ifndef NDEBUG
  ++m_count;
  if (m_count > m_maxCount) {
    m_maxCount = m_count;
  }

  //alog("CREATED LOCAL REF, COUNT: %d, MAX: %d", currentCount(), maxCount());
#endif
}

void JniRefStats::remove() {
#ifndef NDEBUG
  --m_count;

  //alog("DELETED LOCAL REF, COUNT: %d, MAX: %d", currentCount(), maxCount());
#endif
}
