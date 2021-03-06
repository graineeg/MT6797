/*
 * Copyright (C) 2015 The Android Open Source Project
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

package android.media.cts;

import android.cts.util.CtsAndroidTestCase;
import android.media.AudioAttributes;
import android.os.Parcel;

public class AudioAttributesTest extends CtsAndroidTestCase {

    // -----------------------------------------------------------------
    // AUDIOATTRIBUTES TESTS:
    // ----------------------------------

    // -----------------------------------------------------------------
    // Parcelable tests
    // ----------------------------------

    // Test case 1: call describeContents(), not used yet, but needs to be exercised
    public void testParcelableDescribeContents() throws Exception {
        final AudioAttributes aa = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA).build();
        final int c = aa.describeContents();
        assertNotNull("Failure to create the AudioAttributes", aa);
    }

    // Test case 2: create an instance, marshall it and create a new instance,
    //      check for equality, both by comparing fields, and with the equals(Object) method
    public void testParcelableWriteToParcelCreate() throws Exception {
        final AudioAttributes srcAttr = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED).build();
        final Parcel srcParcel = Parcel.obtain();
        final Parcel dstParcel = Parcel.obtain();
        final byte[] mbytes;

        srcAttr.writeToParcel(srcParcel, 0 /*no public flags for marshalling*/);
        mbytes = srcParcel.marshall();
        dstParcel.unmarshall(mbytes, 0, mbytes.length);
        dstParcel.setDataPosition(0);
        final AudioAttributes targetAttr = AudioAttributes.CREATOR.createFromParcel(dstParcel);

        assertEquals("Marshalled/restored usage doesn't match",
                srcAttr.getUsage(), targetAttr.getUsage());
        assertEquals("Marshalled/restored content type doesn't match",
                srcAttr.getContentType(), targetAttr.getContentType());
        assertEquals("Marshalled/restored flags don't match",
                srcAttr.getFlags(), targetAttr.getFlags());
        assertTrue("Source and target attributes are not considered equal",
                srcAttr.equals(targetAttr));
    }
}
