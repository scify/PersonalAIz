/*
 * Copyright 2014 George K. <gkiom@scify.org> 
 *      - SciFY science for you http://www.scify.org
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

package gr.demokritos.iit.recommendationengine.converters.text;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;



/**
 *
 */
public class EnglishStemmer implements IStemmer {
    SnowballStemmer stemmer;
    
    public EnglishStemmer() {
        this.stemmer = new englishStemmer();
    }

    @Override
    public String stem(String sToStem) {
      stemmer.setCurrent(sToStem);
      stemmer.stem();
      return stemmer.getCurrent();          
    }
    
}