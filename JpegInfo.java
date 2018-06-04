
package com.example.xp018347.kotlintest;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import org.apache.commons.imaging.common.bytesource.ByteSourceInputStream;
import org.apache.commons.imaging.formats.jpeg.JpegImageParser;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAscii;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAsciiOrByte;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAsciiOrRational;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoByte;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoByteOrShort;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoDouble;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoFloat;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoGpsText;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoLong;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoRational;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSByte;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSLong;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSRational;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSShort;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShort;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShortOrLong;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShortOrLongOrRational;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShortOrRational;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoXpString;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputField;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class JpegInfo {
    private TiffImageMetadata mMetadata;

    private TiffOutputSet mOutput;

     JpegInfo(File src) throws ImageReadException, IOException {
        mMetadata = parse(new ByteSourceFile(src));
    }

     boolean hasMetadata() {
        if (mMetadata != null) {
            return mMetadata.findDirectory(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT) != null;
        }
        return (mOutput != null);
    }

     boolean contains(TagInfo field) throws ImageReadException {
        boolean ret = false;
        if (mOutput != null) {
            ret = mOutput.findField(field) != null;
        } else if (mMetadata != null) {
            ret = mMetadata.findField(field) != null;
        }
        return ret;
    }

     void rewrite(InputStream jpegData, OutputStream out) throws ImageReadException,
            ImageWriteException, IOException {
        rewrite(jpegData, out, true);
    }

     void rewrite(File jpegData, OutputStream out) throws ImageReadException,
            ImageWriteException, IOException {
        rewrite(jpegData, out, true);
    }

     void rewrite(InputStream jpegData, OutputStream out, boolean lossy)
            throws ImageReadException, ImageWriteException, IOException {
        final ByteSourceInputStream byteSource = new ByteSourceInputStream(jpegData, null);
        rewrite(byteSource, out, lossy);
    }

     void rewrite(File jpegData, OutputStream out, boolean lossy) throws ImageReadException,
            ImageWriteException, IOException {
        final ByteSourceFile byteSource = new ByteSourceFile(jpegData);
        rewrite(byteSource, out, lossy);
    }

     void add(final TagInfoByte tagInfo, final byte... values) throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }


     void add(final TagInfoAscii tagInfo, final String... values) throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoShort tagInfo, final short... values) throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoLong tagInfo, final int... values) throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoRational tagInfo, final RationalNumber... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoSByte tagInfo, final byte... values) throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoSShort tagInfo, final short... values) throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoSLong tagInfo, final int... values) throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }


     void add(final TagInfoSRational tagInfo, final RationalNumber... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }


     void add(final TagInfoFloat tagInfo, final float... values) throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoDouble tagInfo, final double... values) throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoByteOrShort tagInfo, final byte... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoByteOrShort tagInfo, final short... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }


     void add(final TagInfoShortOrLong tagInfo, final short... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }


     void add(final TagInfoShortOrLong tagInfo, final int... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoShortOrLongOrRational tagInfo, final short... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoShortOrLongOrRational tagInfo, final int... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoShortOrLongOrRational tagInfo, final RationalNumber... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoShortOrRational tagInfo, final short... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoShortOrRational tagInfo, final RationalNumber... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoGpsText tagInfo, final String value) throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, value);
    }

     void add(final TagInfoXpString tagInfo, final String value) throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, value);
    }

     void add(final TagInfoAsciiOrByte tagInfo, final String... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoAsciiOrRational tagInfo, final String... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TagInfoAsciiOrRational tagInfo, final RationalNumber... values)
            throws ImageWriteException {
        getDirectoryForTag(tagInfo).add(tagInfo, values);
    }

     void add(final TiffOutputField field) throws ImageWriteException {
        getDirectoryForTag(field.tagInfo).add(field);
    }

     void set(final TagInfoByte tagInfo, final byte... values) throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoAscii tagInfo, final String... values) throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoShort tagInfo, final short... values) throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoLong tagInfo, final int... values) throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoRational tagInfo, final RationalNumber... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoSByte tagInfo, final byte... values) throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoSShort tagInfo, final short... values) throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoSLong tagInfo, final int... values) throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoSRational tagInfo, final RationalNumber... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoFloat tagInfo, final float... values) throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoDouble tagInfo, final double... values) throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoByteOrShort tagInfo, final byte... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoByteOrShort tagInfo, final short... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoShortOrLong tagInfo, final short... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoShortOrLong tagInfo, final int... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoShortOrLongOrRational tagInfo, final short... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoShortOrLongOrRational tagInfo, final int... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoShortOrLongOrRational tagInfo, final RationalNumber... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoShortOrRational tagInfo, final short... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoShortOrRational tagInfo, final RationalNumber... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoGpsText tagInfo, final String value) throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, value);
    }

     void set(final TagInfoXpString tagInfo, final String value) throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, value);
    }

     void set(final TagInfoAsciiOrByte tagInfo, final String... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoAsciiOrRational tagInfo, final String... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TagInfoAsciiOrRational tagInfo, final RationalNumber... values)
            throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(tagInfo);
        dir.removeField(tagInfo);
        dir.add(tagInfo, values);
    }

     void set(final TiffOutputField field) throws ImageWriteException {
        final TiffOutputDirectory dir = getDirectoryForTag(field.tagInfo);
        dir.removeField(field.tagInfo);
        dir.add(field);
    }

    private TiffOutputDirectory getDirectoryForTag(TagInfo ti) throws ImageWriteException {
        final TiffOutputSet os = getOutputSet();
        int dirType;
        if (ti.directoryType == TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN) {
            dirType = TiffDirectoryConstants.DIRECTORY_TYPE_EXIF;
        } else {
            dirType = ti.directoryType.directoryType;
        }
        checkHasRoot();
        TiffOutputDirectory dir = os.findDirectory(dirType);
        if (dir == null) {
            dir = new TiffOutputDirectory(dirType, os.byteOrder);
            os.addDirectory(dir);
        }
        return dir;
    }

    private void rewrite(ByteSource bs, OutputStream out, boolean lossy) throws ImageReadException,
            ImageWriteException, IOException {
        final ExifRewriter exifRewriter = new ExifRewriter();
        if (hasMetadata()) {
            if (lossy) {
                exifRewriter.updateExifMetadataLossy(bs, out, getOutputSet());
            } else {
                exifRewriter.updateExifMetadataLossless(bs, out, getOutputSet());
            }
        } else {
            exifRewriter.removeExifMetadata(bs, out);
        }
    }

    private void checkHasRoot() throws ImageWriteException {
        final TiffOutputDirectory root = getOutputSet().getRootDirectory();
        if (root == null) {
            getOutputSet().addRootDirectory();
        }
    }

    private TiffOutputSet getOutputSet() throws ImageWriteException {
        if (mOutput == null) {
            if (mMetadata == null) {
                mOutput = new TiffOutputSet();
            } else {
                mOutput = mMetadata.getOutputSet();
            }
        }
        return mOutput;
    }

    private TiffImageMetadata parse(ByteSource bs) throws ImageReadException, IOException {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        final JpegImageParser jpegImageParser = new JpegImageParser();
        return jpegImageParser.getExifMetadata(bs, params);
    }

}
