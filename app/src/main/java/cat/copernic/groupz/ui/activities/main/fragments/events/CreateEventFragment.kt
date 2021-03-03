
package cat.copernic.groupz.ui.activities.main.fragments.events

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.app.DatePickerDialog
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentCreateEventBinding
import cat.copernic.groupz.model.Event
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.network.FirebaseClient.Companion.TAG
import cat.copernic.groupz.network.FirebaseClient.Companion.storage
import cat.copernic.groupz.ui.activities.main.fragments.profile.EditProfileFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.regex.Pattern


class CreateEventFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentCreateEventBinding
    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear= 0
    private var GALERI_INTENT = 1000
    private var uri: Uri? = Uri.EMPTY
    private var imgUriFirebase: String = ""
    private lateinit var eventAdd: Event
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateEventBinding.bind(view)
        picKDate()
        val toggleButtonPublic = binding.toggleButtonPublic
        val toggleButtonPrivate = binding.toggleButtonPrivate
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility =
            View.GONE
        toggleButtonPublic.isChecked = true;
        toggleButtonPublic.text = getString(R.string.public_)
        toggleButtonPublic.textOff = getString(R.string.public_)
        toggleButtonPublic.textOn = getString(R.string.public_)
        toggleButtonPrivate.text = getString(R.string.private_)
        toggleButtonPrivate.textOff = getString(R.string.private_)
        toggleButtonPrivate.textOn = getString(R.string.private_)
        toggleButtonPrivate.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            toggleButtonPublic.isChecked = !isChecked
        })
        toggleButtonPublic.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            toggleButtonPrivate.isChecked = !isChecked
        })
        binding.ivEventAdd.setOnClickListener {
            pickImageFromGallery()

        }
        binding.btnSaveEvent.setOnClickListener {
            if (comprovate()) {
                upload()
            }
        }

    }

    private fun comprovate(): Boolean {
        var boolean: Boolean = true
        if (!isValidName()) {
            boolean = false
        }
        if (!isValidDate()) {
            boolean = false
        }

        if (!isValidLocation()) {
            boolean = false
        }
        if (!isValidDescription()) {
            boolean = false
        }

        if (!isValidName()) {
            boolean = false
        }
        if  (uri==Uri.EMPTY){
            boolean = false
            Toast.makeText(context,"debes poner una foto", Toast.LENGTH_LONG).show()
        }
            return boolean

    }

    private fun isValidDate(): Boolean {
        var birthInput = binding.etDateEvent.text.toString()
        val date_pattern: Pattern =
            Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))\$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$");
        if (binding.etDateEvent.text.isEmpty()) {
            binding.etDateEvent.error = getString(R.string.errorEmptyField)
            return false
        } else if (!date_pattern.matcher(birthInput).matches()) {
            binding.etDateEvent.error = getString(R.string.errorDate)
            return false
        } else {
            binding.etDateEvent.error = null
            return true
        }
    }

    private fun isValidLocation(): Boolean {
        if (binding.etLocationEvent.text.isEmpty()) {
            binding.etLocationEvent.error = getString(R.string.errorEmptyField)
            return false
        } else {
            binding.etLocationEvent.error = null
            return true
        }
    }

    private fun isValidDescription(): Boolean {
        if (binding.etDesciptionEvent.text.isEmpty()) {
            binding.etDesciptionEvent.error = getString(R.string.errorEmptyField)
            return false
        } else {
            binding.etDesciptionEvent.error = null
            return true
        }
    }

    private fun isValidName(): Boolean {
        if (binding.etDesciptionEvent.text.isEmpty()) {
            binding.etDesciptionEvent.error = getString(R.string.errorEmptyField)
            return false
        } else {
            binding.etDesciptionEvent.error = null
            return true
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALERI_INTENT)
    }

    private fun upload() {
        var filePath: StorageReference =
            FirebaseStorage.getInstance().getReference().child("communityEvents")
                .child(uri!!.lastPathSegment.toString())
            filePath.putFile(uri!!).addOnSuccessListener {
            var ref=  FirebaseStorage.getInstance().getReference(it.storage.path)
            ref.downloadUrl.addOnSuccessListener {


                var members = arrayListOf<String>()
                members.add(FirebaseClient.auth.currentUser?.email.toString())
                eventAdd = Event(
                    FirebaseClient.auth.currentUser?.email.toString(),
                    binding.etDateEvent.text.toString(),
                    binding.etDesciptionEvent.text.toString(),
                    it.toString(),
                    binding.etLocationEvent.text.toString(),
                    members,
                    binding.etNameEvent.text.toString(),
                    binding.toggleButtonPrivate.isChecked


                )
                if (FirebaseClient.addDatabaseCommunityEvent(eventAdd)) {
                    Log.d(TAG, "Firestore Added Succesfully")
                    Toast.makeText(
                        context,
                        "tu evento se ha creado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                    activity?.onBackPressed()
                }
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALERI_INTENT && resultCode == RESULT_OK) {
            uri = data?.data
            Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.animated_progress)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.ivEventAdd)
            binding.ivIcon.visibility = View.GONE
        }
    }

    private fun getDateCalendar(){

        val cal : Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun picKDate() {
        getDateCalendar()
        binding.etDateEvent.setOnClickListener {
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month+1
        savedYear = year
        binding.etDateEvent.setText("$savedDay/$savedMonth/$savedYear")
    }

}
